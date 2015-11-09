package wszolek.lauren.imagesearch.models;

// google api link: https://developers.google.com/image-search/v1/jsondevguide#json_reference
// shared preferences doc: http://developer.android.com/reference/android/content/SharedPreferences.html

import android.content.Context;
import android.content.SharedPreferences;

public final class SearchFilters {
    // singleton class for storing filter preferences

    private static final String MyPreferences = "MyPrefs";

    private static final String DEFAULT_FILTER_COLOR = "any";
    private static final String DEFAULT_FILTER_SIZE = "any";
    private static final String DEFAULT_FILTER_TYPE = "any";
    private static final String DEFAULT_FILTER_SITE = "";

    private SharedPreferences sharedPref;

    public SearchFilters(Context context) {
        // access to read/write to file system
        sharedPref = context.getApplicationContext().getSharedPreferences("wszolek.lauren.imagesearch.MyPreferences", Context.MODE_PRIVATE);
    }

    private static String imageSize;
    private static String colorFilter;
    private static String imageType;
    private static String siteFilter;

    // get context from main activity once

    public String getColorFilter() {
        colorFilter = sharedPref.getString("color_filter", DEFAULT_FILTER_COLOR);
        return colorFilter;
    }

    public void setColorFilter(String colorFilterNew) {
        colorFilter = colorFilterNew;
    }

    public String getColorFilterUrlArgument() {
        if (colorFilter != null && colorFilter != "any") {
            return "&imgcolor=" + getColorFilter();
        }
        return "";
    }

    public String getImageType() {
        imageType = sharedPref.getString("image_type", DEFAULT_FILTER_TYPE);
        return imageType;
    }

    public void setImageType(String imageTypeNew) {
        imageType = imageTypeNew;
    }

    public String getImageTypeUrlArgument() {
        if (imageType != null && imageType != "any") {
            return "&imgtype=" + getImageType();
        }
        return "";
    }

    public String getSiteFilter() {
        siteFilter = sharedPref.getString("site_filter", DEFAULT_FILTER_SITE);
        return siteFilter;
    }

    public void setSiteFilter(String siteFilterNew) {
        siteFilter = siteFilterNew;
    }

    public String getSiteFilterUrlArgument() {
        if (siteFilter != null && siteFilter != "") {
            return "&as_sitesearch=" + getSiteFilter();
        }
        return "";
    }

    public String getImageSize() {
        imageSize = sharedPref.getString("image_size", DEFAULT_FILTER_SIZE);
        return imageSize;
    }

    public void setImageSize(String imageSizeNew) {
        imageSize = imageSizeNew;
    }

    public String getImageSizeUrlArgument() {
        String sizeStr;
        String imgSz = getImageSize();
        if (imgSz == null || imgSz == "any") {
            return "";
        } else {
            switch (imgSz) {
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

    // gotta push the button if you want to save!
    public void saveFilters() {
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("image_size", imageSize);
        editor.putString("color_filter", colorFilter);
        editor.putString("image_type", imageType);
        editor.putString("site_filter", siteFilter);
        editor.apply();
    }

    public void clearFilters() {
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("image_size", null);
        editor.putString("color_filter", null);
        editor.putString("image_type", null);
        editor.putString("site_filter", null);
        editor.apply();
    }
}