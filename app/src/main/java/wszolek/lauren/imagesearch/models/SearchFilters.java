package wszolek.lauren.imagesearch.models;

// https://developers.google.com/image-search/v1/jsondevguide#json_reference

import android.content.Context;
import android.content.SharedPreferences;

import wszolek.lauren.imagesearch.R;

public final class SearchFilters {
    // singleton class for storing filter preferences

    private static final String DEFAULT_FILTER_COLOR = "any";
    private static final String DEFAULT_FILTER_SIZE = "any";
    private static final String DEFAULT_FILER_TYPE = "any";
    private static final String DEFAULT_FILTER_SITE = "";

    private Context mContext;
    private SearchFilters(Context context) {
        mContext = context;
    }

    // access to read/write to file system
    private SharedPreferences sharedPref = mContext.getSharedPreferences(sFiltersInstance.mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    private SharedPreferences.Editor editor = sharedPref.edit();

    private static SearchFilters sFiltersInstance;


    private static String imageSize;
    private static String colorFilter;
    private static String imageType;
    private static String siteFilter;

    // get context from main activity once

    // my real constructor
    public static SearchFilters getInstance(Context context){
        if(sFiltersInstance  == null) {
            sFiltersInstance = new SearchFilters(context.getApplicationContext());
        }
        return sFiltersInstance;
    }

    public String getColorFilter() {
        colorFilter = sharedPref.getString("color_filter", DEFAULT_FILTER_COLOR);
        return colorFilter;
    }

    public static void setColorFilter(String colorFilterNew) {
        colorFilter = colorFilterNew;
    }

    public static String getColorFilterUrlArgument() {
        if (colorFilter != "any") {
            return "&imgcolor=" + colorFilter;
        }
        return "";
    }

    public String getImageType() {
        imageType = sharedPref.getString("image_type", DEFAULT_FILTER_SIZE);
        return imageType;
    }

    public static void setImageType(String imageTypeNew) {
        imageType = imageTypeNew;
    }

    public static String getImageTypeUrlArgument() {
        if (imageType != "any") {
            return "&imgtype=" + imageType;
        }
        return "";
    }

    public String getSiteFilter() {
        siteFilter = sharedPref.getString("site_filter", DEFAULT_FILTER_SITE);
        return siteFilter;
    }

    public static void setSiteFilter(String siteFilterNew) {
        siteFilter = siteFilterNew;
    }

    public static String getSiteFilterUrlArgument() {
        if (siteFilter != "") {
            return "&as_sitesearch=" + siteFilter;

        }
        return "";
    }

    public String getImageSize() {
        imageSize = sharedPref.getString("image_size", DEFAULT_FILTER_SIZE);
        return imageSize;
    }

    public static void setImageSize(String imageSizeNew) {
        imageSize = imageSizeNew;
    }

    public static String getImageSizeUrlArgument() {
        String sizeStr;
        if (imageSize == "any") {
            return "";
        } else {
            switch (imageSize) {
                case "icon":
                    sizeStr = "icon";
                    break;
                case "medium":
                    sizeStr = "medium";
                    break;
                case "large":
                    sizeStr = "xxlarge";
                    break;
                case "x-large":
                    sizeStr = "huge";
                    break;
                default:
                    return "";
            }
            return "&imgsz=" + sizeStr;
        }
    }

    public String getFilterArguments() {
        return getImageSizeUrlArgument()
                       + getColorFilterUrlArgument()
                       + getImageTypeUrlArgument() + getSiteFilterUrlArgument();

    }

    // TODO move string into string file
    public void saveFilters() {
        editor.putString("image_size", imageSize);
        editor.putString("image_color", colorFilter);
        editor.putString("image_type", imageType);
        editor.putString("site_filter", siteFilter);
        editor.commit();
    }
}