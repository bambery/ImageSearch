package wszolek.lauren.imagesearch.fragments;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View filtersView = inflater.inflate(R.layout.activity_settings, null);
        final SearchFilters sFilters = SearchFilters.getInstance(this.getContext());
        builder.setView(filtersView);

        //populate the size spinner
        Spinner sizeSpinner = (Spinner) filtersView.findViewById(R.id.spSize);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.image_sizes_array, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setSelection(sizeAdapter.getPosition(sFilters.getImageSize()));

        //listen for changes to dropdown
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sFilters.setImageSize(parent.getItemAtPosition(position).toString());
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
        colorSpinner.setSelection(colorAdapter.getPosition(sFilters.getColorFilter()));
        //listen for changes to dropdown
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sFilters.setColorFilter(parent.getItemAtPosition(position).toString());
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
        typeSpinner.setSelection(typeAdapter.getPosition(sFilters.getImageType()));
        //listen for changes to dropdown
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sFilters.setImageType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // don't do anything
            }
        });

        // populate the site filter
        // should set a touch listener on the parent view to capture changes in text, doing hacky
        // workaround because nearly 3am
        final EditText etSiteFilter = (EditText) filtersView.findViewById(R.id.etSiteFilter);
        etSiteFilter.setText(sFilters.getSiteFilter());

        // save your changes
        builder.setMessage(R.string.advanced_filters)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // hacky workaround 
                        sFilters.setSiteFilter(etSiteFilter.getText().toString());
                        // save the filter values
                        sFilters.saveFilters();
                    }
                })
                .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // clear all values set on filter
                        sFilters.clearFilters();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
