package com.example.module_one;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lib_navigator.ActivityEvent;
import com.example.lib_navigator.ActivityEventConnection;
import com.example.lib_navigator.IRouterUri;
import com.example.lib_navigator.Nav;
import com.example.lib_navigator.XLRouter;

@ActivityEventConnection(connect = ActivityEvent.Connect.MainOneActivity)
public class MainOneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_one);
        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String url = "http://sec";
//                Nav.from(MainOneActivity.this).toUri(url);
//                XLRouter.getInstance(MainOneActivity.this).routerUri().jumpToMain();
                Intent intent = new Intent();
                ActivityEvent.jumpTo(MainOneActivity.this, ActivityEvent.Connect.MainSecActivity, intent);
            }
        });
    }
}
