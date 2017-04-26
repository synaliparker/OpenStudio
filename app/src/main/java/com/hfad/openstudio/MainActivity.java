package com.hfad.openstudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNavFindStudio(View view){
        Intent intent = new Intent(this, FindStudioActivity.class);
        startActivity(intent);
    }

    public void onClickAddStudio(View view){
        Intent intent = new Intent(this, AddStudioActivity.class);
        startActivity(intent);
    }

    public void onClickStudioList(View view) {
        Intent intent = new Intent(this, StudioListActivity.class);
        startActivity(intent);
    }

}
