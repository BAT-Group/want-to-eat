package com.example.administrator.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"toast",Toast.LENGTH_LONG);
    }
}
