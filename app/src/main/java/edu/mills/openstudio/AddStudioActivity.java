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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Adapted from Ravi Tamada's
 * <a href="http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/">NewProductActivity</a>.
 */
public class AddStudioActivity extends AppCompatActivity {
    private static final String GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/json";
    private static String ADD_STUDIO_URL = "http://open-studio.herokuapp.com/add_studio.php";
    private static final String TAG_SUCCESS = "success";
    HttpRequestHandler httpRequestHandler = new HttpRequestHandler();
    EditText inputName;
    EditText inputOwner;
    Spinner inputStudioType;
    EditText inputAddress;
    EditText inputEmail;
    EditText inputAvailability;
    Spinner inputAccessibility;
    EditText inputDescription;

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
                        input.setError("Field is required");
                        addStudioButton.setClickable(true);
                        return;
                    }
                }
                String name = inputName.getText().toString();
                String owner = inputOwner.getText().toString();
                String studioType = inputStudioType.getSelectedItem().toString();
                String address = inputAddress.getText().toString();
                String email = inputEmail.getText().toString();
                String availability = inputAvailability.getText().toString();
                String accessibility = inputAccessibility.getSelectedItem().toString();
                String description = inputDescription.getText().toString();
                new PostStudioTask().execute(name, owner, studioType, address, email,
                        availability, accessibility, description);
            }
        });
    }

    private class PostStudioTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... args) {
            double latitude = 0.00;
            double longitude = 0.00;
            try {
                Geocoder geocoder = new Geocoder(AddStudioActivity.this);
                List<Address> addresses;
                addresses = geocoder.getFromLocationName(args[3], 1);
                if (addresses.size() > 0) {
                    latitude = addresses.get(0).getLatitude();
                    longitude = addresses.get(0).getLongitude();
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("address", args[3]);
                    JSONObject json = httpRequestHandler.makeHttpRequest(GEOCODE_URL,
                            "GET", params);
                    try {
                        JSONObject coordinates = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                        latitude = coordinates.getDouble("lat");
                        longitude = coordinates.getDouble("lng");
                    } catch (JSONException e) {
                        return 2;
                    }
                }
            } catch (IOException e) {
                return 2;
            }
            Map<String, String> params = new HashMap<>();
            params.put("name", args[0]);
            params.put("owner", args[1]);
            params.put("type", args[2]);
            params.put("address", args[3]);
            params.put("contact_info", args[4]);
            params.put("availability", args[5]);
            params.put("accessibility", args[6]);
            params.put("description", args[7]);
            params.put("lat", String.valueOf(latitude));
            params.put("lng", String.valueOf(longitude));

            JSONObject json = httpRequestHandler.makeHttpRequest(ADD_STUDIO_URL,
                    "POST", params);
            Log.d("Create Response", json.toString());

            try {
                return json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer status) {
            if (status == 0) {
                Toast toast = Toast.makeText(AddStudioActivity.this, "Failed to add studio", Toast.LENGTH_SHORT);
                toast.show();
                refreshAddForm();
            } else if (status == 1) {
                Toast toast = Toast.makeText(AddStudioActivity.this, "Studio added successfully", Toast.LENGTH_SHORT);
                toast.show();
                refreshAddForm();
            } else if (status == 2) {
                Toast toast = Toast.makeText(AddStudioActivity.this, "Failed to convert address to coordinates", Toast.LENGTH_SHORT);
                toast.show();
                refreshAddForm();
            } else if (status == 3) {
                Toast toast = Toast.makeText(AddStudioActivity.this, "Invalid address", Toast.LENGTH_SHORT);
                toast.show();
                refreshAddForm();
            } else {
                Toast toast = Toast.makeText(AddStudioActivity.this, "JSON error", Toast.LENGTH_SHORT);
                toast.show();
                refreshAddForm();
            }
        }
    }
    void refreshAddForm() {
        Intent i = new Intent(getApplicationContext(), AddStudioActivity.class);
        startActivity(i);
        finish();
    }
}