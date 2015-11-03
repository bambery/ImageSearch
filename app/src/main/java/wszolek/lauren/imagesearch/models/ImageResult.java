package wszolek.lauren.imagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// https://developers.google.com/image-search/v1/jsondevguide#json_reference

public class ImageResult {
    private Integer width;
    private Integer height;
    private String thumbUrl;
    private String url;

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getUrl() {
        return url;
    }

    //new image result passing in raw image json
    public ImageResult(JSONObject json) {
        try {
            this.url = json.getString("url");
            this.height = Integer.parseInt(json.getString("height"));
            this.width = Integer.parseInt(json.getString("width"));
            this.thumbUrl = json.getString("tbUrl");
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    // take array of json image result data and return an arraylist of imageResults
    public static ArrayList<ImageResult> fromJsonArray(JSONArray array){
        ArrayList<ImageResult> imageResults = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            try {
                imageResults.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return imageResults;
    }
}
