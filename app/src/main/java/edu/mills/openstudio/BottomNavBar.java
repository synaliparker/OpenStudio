package edu.mills.openstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class BottomNavBar extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_nav_bar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_item:
                                Intent homeIntent = new Intent(BottomNavBar.this, MainActivity.class);
                                startActivity(homeIntent);
                                break;
                            case R.id.search_studios_item:
                                Intent searchIntent = new Intent(BottomNavBar.this, FindStudioActivity.class);
                                startActivity(searchIntent);
                                break;
                            case R.id.account_item:
                                Intent accountIntent = new Intent(BottomNavBar.this, UserHomeScreenActivity.class);
                                startActivity(accountIntent);
                                break;
                        }
                        return false;
                    }

                });
    }
}