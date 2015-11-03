package wszolek.lauren.imagesearch.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import wszolek.lauren.imagesearch.R;
import wszolek.lauren.imagesearch.models.SearchFilters;


public class SettingsDialogFragment extends DialogFragment{

    // http://developer.android.com/reference/android/app/AlertDialog.Builder.html

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View filtersView = inflater.inflate(R.layout.activity_settings, null);
        builder.setView(filtersView);

        //populate the size spinner
        Spinner sizeSpinner = (Spinner) filtersView.findViewById(R.id.spSize);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.image_sizes_array, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        //populate the color spinner
        Spinner colorSpinner = (Spinner) filtersView.findViewById(R.id.spColors);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.image_colors, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);

        // add buttons
        builder.setMessage(R.string.advanced_filters)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // save the values to the filter
                    }
                })
                .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // clear all values set on filter
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static SettingsDialogFragment newInstance(SearchFilters sFilters) {
        SettingsDialogFragment frag = new SettingsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("filter", sFilters);
        frag.setArguments(args);
        return frag;
    }
}
