package com.pictureviewer.pictureviewer.instagram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pictureviewer.pictureviewer.R;
import com.pictureviewer.pictureviewer.flickr.ViewerFlickr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Viewer extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        new Reader().execute();
    }

    //String declare
    private static final String DATA = "data";
    private static final String CAPTION = "caption";
    private static final String FROM = "from";
    private static final String LOW_RESOLUTION = "low_resolution";
    private static final String IMAGES = "images";
    private static final String URL = "url";
    private static final String TEXT = "text";
    private static final String FULL_NAME = "full_name";

    //data JSONArray
    JSONArray data;

    //Arraylist of Items(class)
    ArrayList<Items> list = new ArrayList<Items>();

    //listView declare
    ListView listView;

    // download dialog
    ProgressDialog download;

    private class Reader extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            download = new ProgressDialog(Viewer.this);
            download.setMessage("Downloading...");
            download.setCancelable(false);
            download.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            StrictMode.ThreadPolicy policy = new StrictMode.
                    ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            final String input = Http.readInstagram();

            try {

                //looping through Array
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

                                //instance Items class
                                final Items items = new Items();

                                //get images
                                try {
                                    items.image = a.getJSONObject(IMAGES).getJSONObject(LOW_RESOLUTION).getString(URL);
                                } catch (Exception e) {

                                }

                                //get text
                                try {
                                    items.text = a.getJSONObject(CAPTION).getString(TEXT);
                                } catch (Exception e) {

                                }
                                //get full name
                                try {
                                    items.full_name = a.getJSONObject(CAPTION).getJSONObject(FROM).getString(FULL_NAME);
                                } catch (Exception e) {

                                }

                                //add data to list
                                list.add(items);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // close download dialog
            if (download.isShowing())
                download.dismiss();

            listView = (ListView) findViewById(R.id.listView);

            //show list in listView
            Viewer.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Adapter adapter = new Adapter(getApplicationContext(), list);
                    listView.setAdapter(adapter);
                }
            });

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
