package wszolek.lauren.imagesearch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import wszolek.lauren.imagesearch.R;
import wszolek.lauren.imagesearch.adapters.ImageResultAdapter;
import wszolek.lauren.imagesearch.fragments.SettingsDialogFragment;
import wszolek.lauren.imagesearch.models.ImageResult;
import wszolek.lauren.imagesearch.models.SearchFilters;

public class ResultActivity extends AppCompatActivity implements SettingsDialogFragment.OnFiltersListener {
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ArrayList<ImageResult> imageResults;
    private ImageResultAdapter aImageResult;

    private SearchFilters searchFilters;
    //keep a copy of the unchanged values
    private SearchFilters searchFiltersOld;

    //links for staggered grid:
    // http://inducesmile.com/android/android-staggeredgridlayoutmanager-example-tutorial/
    // https://developer.android.com/reference/android/support/v7/widget/StaggeredGridLayoutManager.html
    // https://www.bignerdranch.com/blog/recyclerview-part-1-fundamentals-for-listview-experts/

    //link for cloning items in java
    // http://stackoverflow.com/questions/869033/how-do-i-copy-an-object-in-java

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&safe=active&q=";
    //endless scrolling
    private boolean loading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setupViews();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvResults);
        mRecyclerView.setHasFixedSize(true);

        // 3 columns, vertical alignment
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        imageResults = new ArrayList<>();
        searchFilters = new SearchFilters(this);
        searchFiltersOld =  new SearchFilters(searchFilters);
        aImageResult = new ImageResultAdapter(this, imageResults);
        mRecyclerView.setAdapter(aImageResult);

        /*
        //endless scrolling - can't get it to work
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener({
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = staggeredGridLayoutManager.getChildCount();
                totalItemCount = staggeredGridLayoutManager.getItemCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = false;
                        Log.d("tag", "LOAD NEXT ITEM");
                    }
                }
            }
        }));
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // set up the listener for when the searchFilters is performed
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchImages(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_settings){
            launchSettingsFragment();
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchSettingsFragment(){
        SettingsDialogFragment newFragment = SettingsDialogFragment.newInstance(searchFilters);
        FragmentManager fm = getSupportFragmentManager();
        newFragment.show(fm, "settings");
    }

    private void searchImages(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        try {
            client.get(BASE_URL + URLEncoder.encode(query, "utf-8") + searchFilters.getFilterArguments(), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        if (response != null) {
                            JSONObject imagesJSON = response.getJSONObject("responseData"); // don't I need this for pagination?
                            JSONArray results = imagesJSON.getJSONArray("results");
                            imageResults.clear(); // clear existing image results from the array (in cases with new search)
                            imageResults.addAll(ImageResult.fromJsonArray(results));
                            aImageResult.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSizeChanged(String size) {
        searchFilters.setImageSize(size);
    }

    @Override
    public void onColorFilterChanged(String colorFilter) {
        searchFilters.setColorFilter(colorFilter);
    }

    @Override
    public void onItemTypeChanged(String itemType) {
        searchFilters.setImageType(itemType);
    }

    @Override
    public void onSiteFilterChanged(String site){
        searchFilters.setSiteFilter(site);
    }

    @Override
    public void onAllCleared(){
        searchFilters.clearFilters();
    }

    @Override
    public void goodToSave(){
        searchFilters.setGoodToSave(true);
    }

    @Override
    public void checkToSave(){
        // if we changed something, save it in preferences
        if (searchFilters.getGoodToSave() == true){
            searchFilters.saveFilters();
            // reset this value since we have saved
            searchFilters.setGoodToSave(false);
        } else {
            // if we changed nothing, revert to what the filters were before opening the dialog
            searchFilters.clearFilters();
            searchFilters = searchFiltersOld;
        }
    }
}