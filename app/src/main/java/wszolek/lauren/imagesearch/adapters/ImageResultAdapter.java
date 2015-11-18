package wszolek.lauren.imagesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import wszolek.lauren.imagesearch.R;
import wszolek.lauren.imagesearch.activities.ImageFullSize;
import wszolek.lauren.imagesearch.models.ImageResult;

public class ImageResultAdapter extends RecyclerView.Adapter<ImageResultAdapter.ImageHolder> {
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

    // launching an activity from a holder class within a recyclerview
    // http://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivImage;
        private final Context context;

        public ImageHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            ivImage.setOnClickListener(this);
            context = ivImage.getContext();
        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(context, ImageFullSize.class);
            int pos = getLayoutPosition();
            ImageResult mImageResult = imageResults.get(pos);
            intent.putExtra("url", mImageResult.getUrl());
            context.startActivity(intent);
        }
    }

}
