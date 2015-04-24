package com.pictureviewer.pictureviewer.flickr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pictureviewer.pictureviewer.R;
import com.pictureviewer.pictureviewer.instagram.Viewer;

/**
 * Created by Mario on 18.4.2015..
 */
public class ViewerFlickr extends Activity {

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr);
        FlickrNotice();
    }

    //postavlja tekst na polje
    private void FlickrNotice() {
        TextView flickrNotice = (TextView) findViewById(R.id.txtFlickrNotice);
        flickrNotice.setEnabled(true);
        flickrNotice.setText("Trenutno nedostupno \nDostupno s narednom nadogradnjom");
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
                i = new Intent(this, Viewer.class);
                startActivity(i);
                break;
            case R.id.flickr:
                Toast.makeText(this, "Već se nalazite ovdje", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
