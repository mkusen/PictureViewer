package com.pictureviewer.pictureviewer.instagram;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.pictureviewer.pictureviewer.R;
import com.pictureviewer.pictureviewer.flickr.ViewerFlickr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mario on 18.4.2015..
 */
public class ViewerInstagram extends ListActivity {

    //*TODO
    // PODESITI KOD DA HVATA I PARSIRA MOJ JSON*/


    private ProgressDialog pDialog;

    // URL to get data from JSON
    private static String url = "https://api.instagram.com/v1/media/popular?client_id=a957c5b858784410a87de3a90c95484b";

    // JSON Node names
    private static final String DATA = "data";
    private static final String PROFILE_PICTURE = "profile_picture";
    private static final String TEXT = "text";
    private static final String FULL_NAME = "full_name";

    // data JSONArray
    JSONArray data = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);

        dataList = new ArrayList<HashMap<String, String>>();

        ListView listView = getListView();

        // Listview on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String naslov = ((TextView) view.findViewById(R.id.txtText))
                        .getText().toString();
                String autor = ((TextView) view.findViewById(R.id.txtAutor))
                        .getText().toString();

                // Starting single contact activity
                Intent i = new Intent(getApplicationContext(),
                        RedOpsirnije.class);
                i.putExtra(TEXT, naslov);
                i.putExtra(FULL_NAME, autor);
                startActivity(i);

            }
        });

        // Calling async task to get json
        new GetData().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ViewerInstagram.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            Http http = new Http();

            // Making a request to url and getting response
            String jsonStr = http.makeServiceCall(url, Http.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    data = jsonObj.getJSONArray(DATA);

                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);

                        //  String slika = jsonObject.getString(PROFILE_PICTURE);
                        String text = jsonObject.getString(TEXT);
                        String autor = jsonObject.getString(FULL_NAME);


                        // tmp hashmap for single contact
                        HashMap<String, String> user = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        // user.put(PROFILE_PICTURE, slika);
                        user.put(TEXT, text);
                        user.put(FULL_NAME, autor);

                        // adding contact to contact list
                        dataList.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ViewerInstagram.this, dataList,
                    R.layout.activity_instagram_viewer, new String[]{FULL_NAME,
                    TEXT}, new int[]{
                    R.id.txtAutor, R.id.txtText});

            setListAdapter(adapter);
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
