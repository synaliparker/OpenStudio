package edu.mills.openstudio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindStudioActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {
    private GoogleMap googleMap;
    private HashMap<Marker, String> markerMap = new HashMap<>();
    private Double lat = 0.00;
    private Double lng = 0.00;
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LNG = "lng";
    SupportMapFragment fragment;
    ArrayList<HashMap<String,String>> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_studio);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        loadStudios();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_studio:
                //Code to run when the Create Order item is clicked
                Intent intent = new Intent(this, AddStudioActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadStudios() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StudioResponse> call = apiInterface.getAllStudios();
        call.enqueue(new Callback<StudioResponse>() {
            @Override
            public void onResponse(Call<StudioResponse> call, Response<StudioResponse> response) {
                List<Studio> studios = response.body().getStudios();
                for (Studio studio : studios) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(TAG_ID, Integer.toString(studio.getId()));
                    hashMap.put(TAG_NAME, studio.getName());
                    hashMap.put(TAG_TYPE, studio.getType());
                    hashMap.put(TAG_LAT, Double.toString(studio.getLat()));
                    hashMap.put(TAG_LNG, Double.toString(studio.getLng()));
                    locationList.add(hashMap);
                }
                fragment = ((SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map));
                fragment.getMapAsync(FindStudioActivity.this);
            }

            @Override
            public void onFailure(Call<StudioResponse> call, Throwable t) {
                Toast.makeText(FindStudioActivity.this, "Failed to get studios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        setUpMap();
        googleMap.setOnInfoWindowClickListener(this);
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
            String id = locationList.get(i).get(TAG_ID);
            lat = Double.parseDouble(locationList.get(i).get(TAG_LAT));
            lng = Double.parseDouble(locationList.get(i).get(TAG_LNG));
            String name = locationList.get(i).get(TAG_NAME);
            String type = locationList.get(i).get(TAG_TYPE);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(name).snippet(type));
            markerMap.put(marker, id);
        }
    }
    public void onClickStudioList(View view) {
        Intent intent = new Intent(this, StudioListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onInfoWindowClick(Marker marker){
        String studioId = markerMap.get(marker);
        Log.d("id", studioId);
        Intent intent = new Intent(getApplicationContext(), DetailStudioActivity.class);
        intent.putExtra(TAG_ID, studioId);
        startActivity(intent);
    }
}
