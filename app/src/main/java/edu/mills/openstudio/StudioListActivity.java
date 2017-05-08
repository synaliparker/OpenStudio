package edu.mills.openstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Show studios in listview.
 */
public class StudioListActivity extends AppCompatActivity {
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private ListView listView;
    private ArrayList<HashMap<String,String>> studioList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studio_list);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_item:
                                Intent homeIntent = new Intent(StudioListActivity.this, MainActivity.class);
                                startActivity(homeIntent);
                                break;
                            case R.id.search_studios_item:
                                Intent searchIntent = new Intent(StudioListActivity.this, FindStudioActivity.class);
                                startActivity(searchIntent);
                                break;
                            case R.id.account_item:
                                Intent accountIntent = new Intent(StudioListActivity.this, UserHomeScreenActivity.class);
                                startActivity(accountIntent);
                                break;
                        }
                        return false;
                    }

                });
        loadStudios();
    }

    private void loadStudios() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StudioResponse> call = apiInterface.getAllStudios();
        call.enqueue(new Callback<StudioResponse>() {
            @Override
            public void onResponse(Call<StudioResponse> call, Response<StudioResponse> response) {
                if (!response.body().getError()) {
                    List<Studio> studios = response.body().getStudios();
                    for (Studio studio : studios) {
                        String id = Integer.toString(studio.getId());
                        String name = studio.getName();
                        String type = studio.getType();
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(TAG_ID, id);
                        hashMap.put(TAG_NAME, name);
                        hashMap.put(TAG_TYPE,type);
                        studioList.add(hashMap);
                    }
                    listView = (ListView) findViewById(android.R.id.list);
                    ListAdapter adapter = new SimpleAdapter(
                            StudioListActivity.this, studioList,
                            R.layout.single_studio_list, new String[]{
                            TAG_ID, TAG_NAME,TAG_TYPE},
                            new int[]{R.id.id, R.id.name, R.id.type});
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String studioId = ((TextView) view.findViewById(R.id.id)).getText().toString();
                            Intent intent = new Intent(getApplicationContext(), DetailStudioActivity.class);
                            intent.putExtra(TAG_ID, studioId);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(StudioListActivity.this, response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StudioResponse> call, Throwable t) {
                Toast.makeText(StudioListActivity.this, R.string.get_studio_fail, Toast.LENGTH_SHORT).show();
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
}




