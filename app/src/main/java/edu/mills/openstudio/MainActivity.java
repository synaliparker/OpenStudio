package edu.mills.openstudio;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity (new Intent(MainActivity.this, BottomNavBar.class));

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_item:
                                Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(homeIntent);
                                break;
                            case R.id.search_studios_item:
                                Intent searchIntent = new Intent(MainActivity.this, FindStudioActivity.class);
                                startActivity(searchIntent);
                                break;
                            case R.id.account_item:
                                Intent accountIntent = new Intent(MainActivity.this, UserHomeScreenActivity.class);
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
            case R.id.logout:
                // TODO: Add code to run when the logout item is clicked
                return true;
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickFindStudio(View view) {
        Intent intent = new Intent(this, FindStudioActivity.class);
        startActivity(intent);
    }

    public void onClickAddStudio(View view){
        Intent intent = new Intent(this, AddStudioActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}