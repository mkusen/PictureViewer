package com.pictureviewer.pictureviewer.instagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pictureviewer.pictureviewer.R;
import com.pictureviewer.pictureviewer.SplashStart;
import com.pictureviewer.pictureviewer.flickr.ViewerFlickr;

/**
 * Created by Mario on 18.4.2015..
 */
public class ViewerInstagram extends Activity {

    Intent i;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.instagram:
                Toast.makeText(this, "Već se nalazite ovdje", Toast.LENGTH_SHORT).show();
                break;
            case R.id.flickr:
                i = new Intent(this, ViewerFlickr.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
