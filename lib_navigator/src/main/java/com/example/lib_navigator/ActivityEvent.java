package com.example.lib_navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/30.
 */

public class ActivityEvent {
    private static final String TAG = "ActivityEvent";

    public enum Connect {
        None,
        MainActivity,
        MainSecActivity,
        MainOneActivity,
    }

    private static final Map<Connect, Class<? extends Activity>> CONNECT_CLASS_MAP;

    static {
        CONNECT_CLASS_MAP = new HashMap<>();
        CONNECT_CLASS_MAP.put(Connect.None, null); //
    }

    public static void bind(Context context) {
        ActivityInfo[] activities = new ActivityInfo[0];

        try {
            activities = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES)
                    .activities;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (activities != null && activities.length > 0) {
            for (ActivityInfo ai : activities) {
                try {
                    Class<?> type = Class.forName(ai.name);
                    if (!Activity.class.isAssignableFrom(type)) {
                        // This should not happen
                        continue;
                    }

                    ActivityEventConnection anno = type.getAnnotation(ActivityEventConnection.class);
                    if (anno == null) {
                        return;
                    }

                    final Connect connect = anno.connect();
                    if (connect != null && connect != Connect.None) {
                        CONNECT_CLASS_MAP.put(connect, (Class<? extends Activity>) type);
                        Log.d(TAG, String.format("update connect: %s to class %s", connect, type));
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void jumpTo(Context context, Connect connect, Intent intent) {
        Class<? extends Activity> activity = CONNECT_CLASS_MAP.get(connect);
        if (activity == null) {
            Log.e(TAG, String.format("no connection set for %s", connect));
            return;
        }

        if (intent == null) {
            intent = new Intent(context, activity);

        } else {
            intent.setClass(context, activity);
        }

        context.startActivity(intent);
    }
}
