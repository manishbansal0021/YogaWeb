package com.example.hp_pc.yogaweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void yoga(View view)
    {
        Intent i=new Intent(this,ListingActivity.class);
        i.putExtra("what","yoga");
        startActivity(i);
    }
    public void cardio(View view)
    {
        Intent i=new Intent(this,ListingActivity.class);
        i.putExtra("what","exercise");
        startActivity(i);
    }
}
