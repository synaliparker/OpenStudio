package edu.mills.openstudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;

public class DetailStudioActivity extends Activity {
    private TextView txtName;
    private TextView txtType;
    private TextView txtOwner;
    private TextView txtMorePhotos;
    private TextView txtAddress;
    private TextView txtEmail;
    private TextView txtAvailability;
    private TextView txtAccessibility;
    private TextView txtDescription;
    private static final String STUDIO_DETAIL_FAIL = "Failed to get studio details";
    private static final String ID = "id";

    String studioId;
    HttpRequestHandler httpRequestHandler = new HttpRequestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_studio);

        Intent intent = getIntent();
        studioId = intent.getStringExtra(ID);
        Log.d(ID, studioId);
        loadStudio(studioId);
        txtMorePhotos = (TextView) findViewById(R.id.more_photos);
        txtMorePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailStudioActivity.this, ImageDetailActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item){
                        switch (item.getItemId()){
                            case R.id.home_item:
                                Intent homeIntent = new Intent(DetailStudioActivity.this, MainActivity.class);
                                startActivity(homeIntent);
                                break;
                            case R.id.search_studios_item:
                                Intent searchIntent = new Intent(DetailStudioActivity.this, FindStudioActivity.class);
                                startActivity(searchIntent);
                                break;
                            case R.id.account_item:
                                Intent accountIntent = new Intent(DetailStudioActivity.this, UserHomeScreenActivity.class);
                                startActivity(accountIntent);
                                break;
                        }
                        return false;
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
                Toast.makeText(DetailStudioActivity.this, STUDIO_DETAIL_FAIL, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

