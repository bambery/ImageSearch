package wszolek.lauren.imagesearch.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import wszolek.lauren.imagesearch.R;
import wszolek.lauren.imagesearch.models.SearchFilters;


public class SettingsDialogFragment extends DialogFragment {

    // http://developer.android.com/reference/android/app/AlertDialog.Builder.html
    // setting spinner values: http://stackoverflow.com/questions/11072576/set-selected-item-of-spinner-programmatically
    // dialogs and passing data: http://developer.android.com/reference/android/app/DialogFragment.html
    private OnFiltersListener listener;

    private String filterColor;
    private String filterSize;
    private String filterSiteFilter;
    private String filterType;

    public interface OnFiltersListener {
        void onSizeChanged(String color);
        void onColorFilterChanged(String colorFilter);
        void onItemTypeChanged(String itemType);
        void onSiteFilterChanged(String site);
        void onAllCleared();
        void goodToSave();
        void checkToSave();
    }

    public static SettingsDialogFragment newInstance(SearchFilters sFilters){
        SettingsDialogFragment frag = new SettingsDialogFragment();
        Bundle args = new Bundle();
        args.putString("color", sFilters.getColorFilter());
        args.putString("size", sFilters.getImageSize());
        args.putString("type", sFilters.getImageType());
        args.putString("site_filter", sFilters.getSiteFilter());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnFiltersListener) activity;
    }

    @Override
    public void onDestroyView(){
        listener.checkToSave();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        filterColor = getArguments().getString("color");
        filterSize = getArguments().getString("size");
        filterSiteFilter = getArguments().getString("site_filter");
        filterType = getArguments().getString("type");

        // use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View filtersView = inflater.inflate(R.layout.activity_settings, null);
        //final SearchFilters sFilters = new SearchFilters (this.getContext());
        builder.setView(filtersView);

        //populate the size spinner
        Spinner sizeSpinner = (Spinner) filtersView.findViewById(R.id.spSize);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.image_sizes_array, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setSelection(sizeAdapter.getPosition(filterSize));

        //listen for changes to dropdown
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onSizeChanged(parent.getItemAtPosition(position).toString());
                //sFilters.setImageSize(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // don't do anything
            }
        });

        //populate the color spinner
        Spinner colorSpinner = (Spinner) filtersView.findViewById(R.id.spColors);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.image_colors, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);
        colorSpinner.setSelection(colorAdapter.getPosition(filterColor));
        //listen for changes to dropdown
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onColorFilterChanged(parent.getItemAtPosition(position).toString());
                //sFilters.setColorFilter(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // don't do anything
            }
        });

        // populate the type spinner
        // this appears to be no longer supported: https://github.com/hubot-scripts/hubot-google-images/issues/10
        Spinner typeSpinner = (Spinner) filtersView.findViewById(R.id.spType);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.image_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setSelection(typeAdapter.getPosition(filterType));
        //listen for changes to dropdown
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemTypeChanged(parent.getItemAtPosition(position).toString());
                //sFilters.setImageType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // don't do anything
            }
        });

        // populate the site filter
        // should set a touch listener on the parent view to capture changes in text, doing hacky
        // workaround because nearly 3am
        // or do this, but it requires soft keyboard: http://guides.codepath.com/android/Basic-Event-Listeners - OnEditorActionListener
        final EditText etSiteFilter = (EditText) filtersView.findViewById(R.id.etSiteFilter);
        etSiteFilter.setText(filterSiteFilter);

        // save your changes

        builder.setMessage(R.string.advanced_filters)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String enteredSiteFilter = etSiteFilter.getText().toString();
                        if (!(filterSiteFilter.equals(enteredSiteFilter))) {
                            listener.onSiteFilterChanged(enteredSiteFilter);
                        }
                        //remove these listeners and just use dopostitive/negative clicks
                        listener.goodToSave();
                    }
                })
                .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //reset to defaults
                        listener.onAllCleared();
                        //keep the reset values in memory and persist to file system
                        listener.goodToSave();

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
