package wszolek.lauren.imagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import wszolek.lauren.imagesearch.R;

public class ImageFullSize extends AppCompatActivity {
    String url;
    ImageView ivImageFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_size);
        url = getIntent().getExtras().getString("url");
        ivImageFull = (ImageView) findViewById(R.id.ivImageFull);
        Picasso.with(this).load(url).into(ivImageFull);
    }
}
