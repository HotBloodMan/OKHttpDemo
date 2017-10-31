package com.ljt.activitymodels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;

@Router("books_list")
public class MyModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_model);
    }
}
