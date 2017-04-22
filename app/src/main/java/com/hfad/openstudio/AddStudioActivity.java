package com.hfad.openstudio;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Adapted from Ravi Tamada's
 * <a href="http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/">NewProductActivity</a>.
 */
public class AddStudioActivity extends Activity {
    private static String addStudioPath = "add_studio.php";
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
    EditText inputLat;
    EditText inputLng;

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
        inputLat = (EditText) findViewById(R.id.lat);
        inputLng = (EditText) findViewById(R.id.lng);

        Button addStudioButton = (Button) findViewById(R.id.add_studio);
        addStudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String owner = inputOwner.getText().toString();
                String studioType = inputStudioType.getSelectedItem().toString();
                String address = inputDescription.getText().toString();
                String email = inputEmail.getText().toString();
                String availability = inputAvailability.getText().toString();
                String accessibility = inputAccessibility.getSelectedItem().toString();
                String description = inputDescription.getText().toString();
                String lat = inputLat.getText().toString();
                String lng = inputLng.getText().toString();
                new PostStudioTask().execute(name, owner, studioType, address, email,
                        availability, accessibility, description, lat, lng);
            }
        });
    }

    private class PostStudioTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... args) {
            Map<String, String> params = new HashMap<>();
            params.put("name", args[0]);
            params.put("owner", args[1]);
            params.put("type", args[2]);
            params.put("address", args[3]);
            params.put("contact_info", args[4]);
            params.put("availability", args[5]);
            params.put("accessibility", args[6]);
            params.put("description", args[7]);
            params.put("lat", args[8]);
            params.put("lng", args[9]);

            JSONObject json = httpRequestHandler.makeHttpRequest(addStudioPath,
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
            if (status == 1) {
                Toast toast = Toast.makeText(AddStudioActivity.this, "Studio added successfully", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(getApplicationContext(), AddStudioActivity.class);
                startActivity(i);
                finish();
            } else if (status == 0) {
                Toast toast = Toast.makeText(AddStudioActivity.this, "Failed to add studio", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                    Toast toast = Toast.makeText(AddStudioActivity.this, "JSON error", Toast.LENGTH_SHORT);
                    toast.show();
            }
        }
    }
}