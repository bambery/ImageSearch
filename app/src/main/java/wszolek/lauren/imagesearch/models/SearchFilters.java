package wszolek.lauren.imagesearch.models;

public class SearchFilters {
    private String imageSize;
    private String colorFilter;
    private String imageType;
    private String siteFilter;

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getColorFilterUrlArgument(){
        if(colorFilter != null) {
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
        if(imageType != null){
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
        if(siteFilter != null){
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
        if(imageSize != null){
            return "&imgsz=" + imageSize;
        }
        return "";
    }

    public String getFilterArguments(){
        return getImageSizeUrlArgument()
                       + getColorFilterUrlArgument()
                       + getImageTypeUrlArgument() + getSiteFilterUrlArgument();

    }
}
