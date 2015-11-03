package wszolek.lauren.imagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

// https://developers.google.com/image-search/v1/jsondevguide#json_reference

public class SearchFilters implements Parcelable {
    private String imageSize = "any";
    private String colorFilter = "any";
    private String imageType = "any";
    private String siteFilter = "";

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getColorFilterUrlArgument(){
        if(colorFilter != "any") {
            return "&imgcolor=" + colorFilter;
        }
        return "";
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageTypeUrlArgument(){
        if(imageType != "any"){
            return "&imgtype=" + imageType;
        }
        return "";
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

    public String getSiteFilterUrlArgument(){
        if(siteFilter != ""){
            return "&as_sitesearch=" + siteFilter;

        }
        return "";
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageSizeUrlArgument(){
        String sizeStr;
        if(imageSize == "any") {
            return "";
        } else {
            switch(imageSize){
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

    public String getFilterArguments(){
        return getImageSizeUrlArgument()
                       + getColorFilterUrlArgument()
                       + getImageTypeUrlArgument() + getSiteFilterUrlArgument();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageSize);
        dest.writeString(this.colorFilter);
        dest.writeString(this.imageType);
        dest.writeString(this.siteFilter);
    }

    public SearchFilters() {
    }

    protected SearchFilters(Parcel in) {
        this.imageSize = in.readString();
        this.colorFilter = in.readString();
        this.imageType = in.readString();
        this.siteFilter = in.readString();
    }

    public static final Parcelable.Creator<SearchFilters> CREATOR = new Parcelable.Creator<SearchFilters>() {
        public SearchFilters createFromParcel(Parcel source) {
            return new SearchFilters(source);
        }

        public SearchFilters[] newArray(int size) {
            return new SearchFilters[size];
        }
    };
}
