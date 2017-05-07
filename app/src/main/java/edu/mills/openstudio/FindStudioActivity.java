package edu.mills.openstudio;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindStudioActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    private GoogleMap googleMap;
    GoogleApiClient mapGoogleApiClient;
    Location mapLastLocation;
    Marker mapCurrentLocationMarker;
    LocationRequest mapLocationRequest;

    private HashMap<Marker, String> markerMap = new HashMap<>();
    private Double lat = 0.00;
    private Double lng = 0.00;
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LNG = "lng";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int LOCATION_REFRESH = 1000;
    private static final String GET_STUDIO_FAIL = "Failed to get studios";
    private static final String PERMISSION_DENIED = "permission denied";

    SupportMapFragment fragment;
    ArrayList<HashMap<String,String>> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_studio);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(FindStudioActivity.this, AddStudioActivity.class);
                startActivity(intent);
            }
        });
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
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkLocationPermission();
                }
                fragment = ((SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map));
                fragment.getMapAsync(FindStudioActivity.this);
            }

            @Override
            public void onFailure(Call<StudioResponse> call, Throwable t) {
                Toast.makeText(FindStudioActivity.this, GET_STUDIO_FAIL, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        setUpMap();

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
            }
        }else{
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.setOnInfoWindowClickListener(this);
    }

    protected synchronized void buildGoogleApiClient(){
        mapGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mapGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle){
        mapLocationRequest = new LocationRequest();
        mapLocationRequest.setInterval(LOCATION_REFRESH);
        mapLocationRequest.setFastestInterval(LOCATION_REFRESH);
        mapLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mapGoogleApiClient,
                    mapLocationRequest,this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void setUpMap() {
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
        Log.d(TAG_ID, studioId);
        Intent intent = new Intent(getApplicationContext(), DetailStudioActivity.class);
        intent.putExtra(TAG_ID, studioId);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        mapLastLocation = location;
        if (mapCurrentLocationMarker != null) {
            mapCurrentLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mapGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mapGoogleApiClient, this);
        }

    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mapGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, PERMISSION_DENIED, Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

}
