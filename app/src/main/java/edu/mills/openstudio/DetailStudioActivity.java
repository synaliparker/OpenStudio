package edu.mills.openstudio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailStudioActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static final String STUDIO_DETAIL_URL = "http://open-studio.herokuapp.com/get_studio_details.php?id=";
    private TextView txtName;
    private TextView txtType;
    private TextView txtOwner;
    private TextView txtMorePhotos;
    private TextView txtAddress;
    private TextView txtEmail;
    private TextView txtAvailability;
    private TextView txtAccessibility;
    private TextView txtDescription;

    private static final String TAG_STUDIO = "studio";
    private static final String TAG_NAME = "name";
    private static final String TAG_ID = "id";
    private static final String TAG_TYPE = "type";
    private static final String TAG_OWNER = "owner";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_CONTACT = "contact_info";
    private static final String TAG_AVAIL = "availability";
    private static final String TAG_ACCESS = "accessibility";
    private static final String TAG_DESCRIPTION = "description";

    String studioId;
    HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_studio);

        Intent intent = getIntent();
        studioId = intent.getStringExtra(TAG_ID);
        Log.d("ID", studioId);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        getStudioDetails(studioId.toString());
//        new GetStudioDetails().execute();
        txtMorePhotos = (TextView) findViewById(R.id.more_photos);
        txtMorePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailStudioActivity.this, ImageDetailActivity.class);
                startActivity(intent);
            }
        });
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

    private void getStudioDetails(final String studioId) {
        // Tag used to cancel the request
        String tag_string_req = "req_details";

        pDialog.setMessage("Getting studio details ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                STUDIO_DETAIL_URL + studioId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONArray studioArray = jObj.getJSONArray(TAG_STUDIO);
                        JSONObject studio = studioArray.getJSONObject(0);
                        txtName = (TextView) findViewById(R.id.studio_name);
                        txtType = (TextView) findViewById(R.id.studio_type);
                        txtOwner = (TextView) findViewById(R.id.studio_owner);
                        txtAddress = (TextView) findViewById(R.id.studio_address);
                        txtEmail = (TextView) findViewById(R.id.studio_contact);
                        txtAvailability = (TextView) findViewById(R.id.studio_availability);
                        txtAccessibility = (TextView) findViewById(R.id.studio_accessibility);
                        txtDescription = (TextView) findViewById(R.id.studio_description);

                        txtName.setText(studio.getString(TAG_NAME));
                        txtType.setText(studio.getString(TAG_TYPE));
                        txtOwner.setText(studio.getString(TAG_OWNER));
                        txtAddress.setText("Address: " + studio.getString(TAG_ADDRESS));
                        txtEmail.setText("Email: " + studio.getString(TAG_CONTACT));
                        txtAvailability.setText("Availability: " + studio.getString(TAG_AVAIL));
                        txtAccessibility.setText("Accessibility: " + studio.getString(TAG_ACCESS));
                        txtDescription.setText("Description: " + studio.getString(TAG_DESCRIPTION));
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

//            @Override
//            protected Map<String, String> getParams() {
//                // Posting parameters to login url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", studioId);
//
//                return params;
//            }

        };

        // Adding request to request queue
        Log.d("Status", strReq.toString());
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

//    /**
//     * Background Async Task to Get complete product details
//     * */
//    class GetStudioDetails extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         * */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(DetailStudioActivity.this);
//            pDialog.setMessage("Loading studio details. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        /**
//         * Getting product details in background thread
//         * */
//        protected String doInBackground(String... params) {
//
//            // updating UI from Background Thread
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    // Check for success tag
//                    int success;
//                    try {
//                        // Building Parameters
//                        HashMap<String, String> hashMap = new HashMap<>();
//                        hashMap.put(TAG_ID, studio_id.toString());
//
//                        // getting product details by making HTTP request
//                        // Note that product details url will use GET request
//                        JSONObject json = httpRequestHandler.makeHttpRequest(
//                                STUDIO_DETAIL_PATH, "GET", hashMap);
//                        Log.d("JSON", json.toString());
//
//                        // json success tag
//                        try {
//                            success = json.getInt(TAG_SUCCESS);
//                            Log.d("success", String.valueOf(success));
//                        } catch (JSONException e) {
//                            Log.d("Error", "JSON exception");
//                            success = 3;
//                        }
//                        if (success == 1) {
//                            // successfully received product details
//                            JSONArray studioDetails = json
//                                    .getJSONArray(TAG_STUDIOS); // JSON Array
//                            JSONObject studio = studioDetails.getJSONObject(0);
//                            for (int i = 0; i < studioDetails.length(); i++) {
//                                Log.d("id tag", studioDetails.getJSONObject(i).getString(TAG_ID));
//                                if (studioDetails.getJSONObject(i).getString(TAG_ID).equals(studio_id)) {
//                                    studio = studioDetails.getJSONObject(i);
//                                }
//                            }
//
//                            // product with this pid found
//                            // Edit Text
//                            txtName = (TextView) findViewById(R.id.studio_name);
//                            txtType = (TextView) findViewById(R.id.studio_type);
//                            txtOwner = (TextView) findViewById(R.id.studio_owner);
//                            txtAddress = (TextView) findViewById(R.id.studio_address);
//                            txtEmail = (TextView) findViewById(R.id.studio_contact);
//                            txtAvailability = (TextView) findViewById(R.id.studio_availability);
//                            txtAccessibility = (TextView) findViewById(R.id.studio_accessibility);
//                            txtDescription = (TextView) findViewById(R.id.studio_description);
//
//                            // display product data in EditText
//                            txtName.setText(studio.getString(TAG_NAME));
//                            txtType.setText(studio.getString(TAG_TYPE));
//                            txtOwner.setText(studio.getString(TAG_OWNER));
//                            txtAddress.setText("Address: " + studio.getString(TAG_ADDRESS));
//                            txtEmail.setText("Email: " + studio.getString(TAG_CONTACT));
//                            txtAvailability.setText("Availability: " + studio.getString(TAG_AVAIL));
//                            txtAccessibility.setText("Accessibility: " + studio.getString(TAG_ACCESS));
//                            txtDescription.setText("Description: " + studio.getString(TAG_DESCRIPTION));
//
//                        }else{
//                            // product with pid not found
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            return null;
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         * **/
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog once got all details
//            pDialog.dismiss();
//        }
//    }

