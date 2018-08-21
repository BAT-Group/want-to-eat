package com.example.administrator.myapplication.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setToolbar() {
        toolbar.setTitle("WantToEat");
        setSupportActionBar(toolbar);
    }
}
