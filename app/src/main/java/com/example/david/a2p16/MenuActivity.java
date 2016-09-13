package com.example.david.a2p16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String receivedName = getIntent().getStringExtra(MainActivity.EXTRA_Name); //Retrieves Name
        String receivedEmail = getIntent().getStringExtra(MainActivity.EXTRA_Email); //Retrieves Email

        setTitle(receivedEmail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);
    }
}
