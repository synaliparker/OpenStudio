package com.hfad.openstudio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindStudioActivity extends FragmentActivity implements OnMapReadyCallback {
    private ProgressDialog progressDialog;
    private GoogleMap googleMap;
    private Double lat = 0.00;
    private Double lng = 0.00;
    private static String GET_URL_PATH = "get_all_studios.php";
    private static final String TAG_STUDIOS = "studios";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LNG = "lng";
    private static final String TAG_SUCCESS = "success";
    HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
    SupportMapFragment fragment;
    ArrayList<HashMap<String,String>> locationList = new ArrayList<>();
    JSONArray locationArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_studio);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new GetLocationTask().execute();
    }

    class GetLocationTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String...args) {
            Map<String, String> params = new HashMap<>();

            JSONObject json = httpRequestHandler.makeHttpRequest(GET_URL_PATH, "GET", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    locationArray = json.getJSONArray(TAG_STUDIOS);

                    for (int i = 0; i < locationArray.length(); i++) {
                        JSONObject jsonObject = locationArray.getJSONObject(i);
                        HashMap<String, String> hashMap = new HashMap<String,String>();
                        hashMap.put(TAG_ID,jsonObject.getString(TAG_ID));
                        hashMap.put(TAG_NAME,jsonObject.getString(TAG_NAME));
                        hashMap.put(TAG_LAT,jsonObject.getString(TAG_LAT));
                        hashMap.put(TAG_LNG,jsonObject.getString(TAG_LNG));
                        locationList.add(hashMap);
                    }
                }else {
                    Intent intent = new Intent(getApplicationContext(), AddStudioActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url){
            fragment = ((SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map));
            fragment.getMapAsync(FindStudioActivity.this);
        }


    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        setUpMap();
    }

    public void setUpMap() {
        // focus & zoom
        lat = 37.77;
        lng = -122.18;
        LatLng coordinate = new LatLng(lat, lng);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 11));

        // marker loop
        for (int i = 0; i < locationList.size(); i++) {
            lat = Double.parseDouble(locationList.get(i).get(TAG_LAT));
            lng = Double.parseDouble(locationList.get(i).get(TAG_LNG));
            String name = locationList.get(i).get(TAG_NAME);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng)).title(name);
            googleMap.addMarker(marker);
        }
    }

    public void onClickStudioList(View view) {
        Intent intent = new Intent(this, StudioListActivity.class);
        startActivity(intent);
    }
}
