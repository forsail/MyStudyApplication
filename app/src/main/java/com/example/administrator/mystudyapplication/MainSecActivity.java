package com.example.administrator.mystudyapplication;

import android.app.Activity;
import android.os.Bundle;

import com.example.lib_navigator.ActivityEvent;
import com.example.lib_navigator.ActivityEventConnection;

/**
 * Created by Administrator on 2016/12/28.
 */
@ActivityEventConnection(connect = ActivityEvent.Connect.MainSecActivity)
public class MainSecActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
