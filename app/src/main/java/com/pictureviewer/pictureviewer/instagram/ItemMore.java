package com.pictureviewer.pictureviewer.instagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pictureviewer.pictureviewer.R;

/**
 * Created by Mario on 24.4.2015..
 */
public class ItemMore extends Activity {

    private static final String LOW_RESOLUTION = "low_resolution";
    private static final String TEXT = "text";
    private static final String FULL_NAME = "full_name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_more);
        Opsirnije();

    }

    /*TODO
    dovršiti postavljanje slike na novi ekran*/


    private void Opsirnije() {

        Intent i = getIntent();

        ImageView imgLowResPic = (ImageView) findViewById(R.id.imgThumbnail);
        TextView txtFullName = (TextView) findViewById(R.id.txtFullName);
        TextView txtText = (TextView) findViewById(R.id.txtText);

        String image = i.getStringExtra(LOW_RESOLUTION);
        String full_name = i.getStringExtra(FULL_NAME);
        String text = String.valueOf(i.getStringExtra(TEXT));

        ImageLoader.getInstance().displayImage(image, imgLowResPic);
        txtFullName.setText(full_name);
        txtText.setText(text);

    }

}
