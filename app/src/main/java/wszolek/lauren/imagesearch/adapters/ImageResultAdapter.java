package wszolek.lauren.imagesearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import wszolek.lauren.imagesearch.R;
import wszolek.lauren.imagesearch.holders.ImageHolder;
import wszolek.lauren.imagesearch.models.ImageResult;

public class ImageResultAdapter extends RecyclerView.Adapter<ImageHolder> {
    private ArrayList<ImageResult> imageResults;
    private Context context;

    public ImageResultAdapter(Context context, ArrayList<ImageResult> imageResults) {
        this.imageResults = imageResults;
        this.context = context;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_result, null);
        ImageHolder imageHolder = new ImageHolder(layoutView);
        return imageHolder;
    }

    @Override
    public void onBindViewHolder(ImageHolder imageHolder, int position) {
        ImageResult imageResult = imageResults.get(position);
        Picasso.with(context)
                .load(imageResult.getThumbUrl())
// implement this in a min
//                .placeholder(R.drawable.placeholder)
                .into(imageHolder.ivImage);
    }

    @Override
    public int getItemCount() {
        return this.imageResults.size();
    }

}
