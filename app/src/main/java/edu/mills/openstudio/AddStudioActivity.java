package edu.mills.openstudio;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Form for users to add new studios.
 */
public class AddStudioActivity extends AppCompatActivity {
    private static final String GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/json";
    private EditText inputName;
    private EditText inputOwner;
    private Spinner inputStudioType;
    private EditText inputAddress;
    private EditText inputEmail;
    private EditText inputAvailability;
    private Spinner inputAccessibility;
    private EditText inputDescription;
    String name;
    String owner;
    String studioType;
    String address;
    String email;
    String availability;
    String accessibility;
    String description;
    HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
    private static final String REQUIRED_FIELD = "Field is required";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lng";
    private static final String GET = "GET";
    private static final String RESULTS = "results";
    private static final String GEOMETRY = "geometry";
    private static final String ADDRESS = "address";
    private static final String LOCATION = "location";
    private static final String ON_POST_EXECUTE = "onPostExecute";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_studio);

        inputName = (EditText) findViewById(R.id.name);
        inputOwner = (EditText) findViewById(R.id.owner);
        inputStudioType = (Spinner) findViewById(R.id.type);
        inputAddress = (EditText) findViewById(R.id.address);
        inputEmail = (EditText) findViewById(R.id.contactInfo);
        inputAvailability = (EditText) findViewById(R.id.availability);
        inputAccessibility = (Spinner) findViewById(R.id.accessibility);
        inputDescription = (EditText) findViewById(R.id.description);

        final Button addStudioButton = (Button) findViewById(R.id.add_studio);
        addStudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudioButton.setClickable(false);
                EditText[] inputs = {inputName, inputOwner, inputAddress, inputEmail, inputAvailability, inputDescription};
                for (EditText input : inputs) {
                    if (input.getText().toString().length() == 0) {
                        input.setError(REQUIRED_FIELD);
                        addStudioButton.setClickable(true);
                        return;
                    }
                }
                name = inputName.getText().toString();
                owner = inputOwner.getText().toString();
                studioType = inputStudioType.getSelectedItem().toString();
                address = inputAddress.getText().toString();
                email = inputEmail.getText().toString();
                availability = inputAvailability.getText().toString();
                accessibility = inputAccessibility.getSelectedItem().toString();
                description = inputDescription.getText().toString();
                new ConvertLocationTask().execute(address);
            }
        });
    }

    private void postStudio(Double lat, Double lng) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StudioResponse> call = apiInterface.addStudio(name, owner, studioType,
                address, email, availability, accessibility, description, lat.toString(), lng.toString());
        call.enqueue(new Callback<StudioResponse>() {
            @Override
            public void onResponse(Call<StudioResponse> call, Response<StudioResponse> response) {
                if (!response.body().getError()) {
                    Toast.makeText(AddStudioActivity.this, R.string.add_studio_success, Toast.LENGTH_SHORT).show();
                    refreshAddForm();
                } else {
                    Toast.makeText(AddStudioActivity.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                    refreshAddForm();
                }
            }

            @Override
            public void onFailure(Call<StudioResponse> call, Throwable t) {
                Toast.makeText(AddStudioActivity.this, R.string.add_studio_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ConvertLocationTask extends AsyncTask<String, Void, HashMap<String, Double>> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected HashMap<String, Double> doInBackground(String... args) {
            HashMap<String, Double> coordinates = new HashMap<>();
            try {
                Geocoder geocoder = new Geocoder(AddStudioActivity.this);
                List<Address> addresses;
                addresses = geocoder.getFromLocationName(args[0], 1);
                if (addresses.size() > 0) {
                    coordinates.put(LATITUDE, addresses.get(0).getLatitude());
                    coordinates.put(LONGITUDE, addresses.get(0).getLongitude());
                    return coordinates;
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put(ADDRESS, args[0]);
                    JSONObject json = httpRequestHandler.makeHttpRequest(GEOCODE_URL,
                            GET, params);
                    try {
                        JSONObject results = json.getJSONArray(RESULTS).getJSONObject(0).getJSONObject(GEOMETRY).getJSONObject(LOCATION);
                        coordinates.put(LATITUDE, results.getDouble(LATITUDE));
                        coordinates.put(LONGITUDE, results.getDouble(LONGITUDE));
                        return coordinates;
                    } catch (JSONException e) {
                        return null;
                    }
                }
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String, Double> coordinates) {
            if (coordinates.isEmpty()) {
                Toast.makeText(AddStudioActivity.this, R.string.convert_studio_fail, Toast.LENGTH_SHORT).show();
                refreshAddForm();
            } else {
                Log.d(ON_POST_EXECUTE, coordinates.toString());
                postStudio(coordinates.get(LATITUDE), coordinates.get(LONGITUDE));
            }
        }
    }
    void refreshAddForm() {
        Intent i = new Intent(getApplicationContext(), AddStudioActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                //Code to run when the about item is clicked
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}