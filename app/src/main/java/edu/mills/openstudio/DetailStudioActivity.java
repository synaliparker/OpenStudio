package edu.mills.openstudio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

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
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        getStudioDetails(studioId.toString());
        loadStudio(studioId);
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

    private void loadStudio(String studioId) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StudioResponse> call = apiInterface.getStudioDetails(studioId);
        call.enqueue(new Callback<StudioResponse>() {
            @Override
            public void onResponse(Call<StudioResponse> call, retrofit2.Response<StudioResponse> response) {
                Studio studio = response.body().getStudios().get(0);
                txtName = (TextView) findViewById(R.id.studio_name);
                txtType = (TextView) findViewById(R.id.studio_type);
                txtOwner = (TextView) findViewById(R.id.studio_owner);
                txtAddress = (TextView) findViewById(R.id.studio_address);
                txtEmail = (TextView) findViewById(R.id.studio_contact);
                txtAvailability = (TextView) findViewById(R.id.studio_availability);
                txtAccessibility = (TextView) findViewById(R.id.studio_accessibility);
                txtDescription = (TextView) findViewById(R.id.studio_description);

                txtName.setText(studio.getName());
                txtType.setText(studio.getType());
                txtOwner.setText(studio.getOwner());
                txtAddress.setText("Address: " + studio.getAddress());
                txtEmail.setText("Email: " + studio.getContactInfo());
                txtAvailability.setText("Availability: " + studio.getAvailability());
                txtAccessibility.setText("Accessibility: " + studio.getAccessibility());
                txtDescription.setText("Description: " + studio.getDescription());
            }

            @Override
            public void onFailure(Call<StudioResponse> call, Throwable t) {
                Toast.makeText(DetailStudioActivity.this, "Failed to get studio details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

//    private void getStudioDetails(final String studioId) {
//        // Tag used to cancel the request
//        String tag_string_req = "req_details";
//
//        pDialog.setMessage("Getting studio details ...");
//        showDialog();
//
//        StringRequest strReq = new StringRequest(Request.Method.GET,
//                STUDIO_DETAIL_URL + studioId, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Response: " + response.toString());
//                hideDialog();
//
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean error = jObj.getBoolean("error");
//
//                    // Check for error node in json
//                    if (!error) {
//                        JSONArray studioArray = jObj.getJSONArray(TAG_STUDIO);
//                        JSONObject studio = studioArray.getJSONObject(0);
//                        txtName = (TextView) findViewById(R.id.studio_name);
//                        txtType = (TextView) findViewById(R.id.studio_type);
//                        txtOwner = (TextView) findViewById(R.id.studio_owner);
//                        txtAddress = (TextView) findViewById(R.id.studio_address);
//                        txtEmail = (TextView) findViewById(R.id.studio_contact);
//                        txtAvailability = (TextView) findViewById(R.id.studio_availability);
//                        txtAccessibility = (TextView) findViewById(R.id.studio_accessibility);
//                        txtDescription = (TextView) findViewById(R.id.studio_description);
//
//                        txtName.setText(studio.getString(TAG_NAME));
//                        txtType.setText(studio.getString(TAG_TYPE));
//                        txtOwner.setText(studio.getString(TAG_OWNER));
//                        txtAddress.setText("Address: " + studio.getString(TAG_ADDRESS));
//                        txtEmail.setText("Email: " + studio.getString(TAG_CONTACT));
//                        txtAvailability.setText("Availability: " + studio.getString(TAG_AVAIL));
//                        txtAccessibility.setText("Accessibility: " + studio.getString(TAG_ACCESS));
//                        txtDescription.setText("Description: " + studio.getString(TAG_DESCRIPTION));
//                    } else {
//                        // Error in login. Get the error message
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//        };
//
//        // Adding request to request queue
//        Log.d("Status", strReq.toString());
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }
//
//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }

