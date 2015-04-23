package com.pictureviewer.pictureviewer.instagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pictureviewer.pictureviewer.R;
import com.pictureviewer.pictureviewer.flickr.ViewerFlickr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mario on 18.4.2015..
 */
public class ViewerInstagram extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        Reader();
    }

    private static final String DATA = "data";
    private static final String CAPTION = "caption";
    private static final String FROM = "from";
    private static final String THUMBNAIL = "thumbnail";
    private static final String IMAGES = "images";
    private static final String URL = "url";
    private static final String TEXT = "text";
    private static final String FULL_NAME = "full_name";

    // data JSONArray
    JSONArray data;

    ArrayList<Items> list = new ArrayList<Items>();

    ListView listView;
    TextView txtText;
    TextView txtFullName;
    ImageView imgSlika;

    public void Reader() {

        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String input = Http.readInstagram();

        try {
            JSONArray json = new JSONArray(input);
            for (int j = 0; j < json.length(); j++) {
                JSONObject jsonObj = json.getJSONObject(j);
                if (json != null) {
                    try {

                        // Getting JSON Array node
                        data = jsonObj.getJSONArray(DATA);
                        // looping through All Objects
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject a = data.getJSONObject(i);

                            try {

                                Items items = new Items();
                                items.image = a.getJSONObject(IMAGES).getJSONObject(THUMBNAIL).getString(URL);
                                items.text = a.getJSONObject(CAPTION).getString(TEXT);
                                items.full_name = a.getJSONObject(CAPTION).getJSONObject(FROM).getString(FULL_NAME);
                                list.add(items);

                            } catch (Exception e) {
                                txtFullName = (TextView) findViewById(R.id.txtFullName);
                                txtText = (TextView) findViewById(R.id.txtText);
                                if (TEXT == null) {
                                    txtText.setText("nema naslova");
                                }
                                if (FULL_NAME == null) {
                                    txtFullName.setText("nema imena");
                                }
                            }

                            System.out.println("lista " + list.toString());

                            listView = (ListView) findViewById(R.id.listView);

                            ViewerInstagram.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Adapter adapter = new Adapter(getApplicationContext(), list);
                                    listView.setAdapter(adapter);
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
                Log.i(ViewerInstagram.class.getName(), json.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Intent i = new Intent(this, ViewerFlickr.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
