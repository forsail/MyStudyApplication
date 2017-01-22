package com.example.administrator.mystudyapplication;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.example.lib_navigator.ActivityEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/11/15.
 */

public class MyAppication extends Application {
    private static MyAppication instance;
    private static BaseComponent baseComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 拿到原始的 mInstrumentation字段
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

            // 创建代理对象
            Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

            // 偷梁换柱
            mInstrumentationField.set(currentActivityThread, evilInstrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        baseComponent = DaggerBaseComponent.builder().baseModule(new BaseModule()).build();
        ActivityEvent.bind(this);
    }

    public static MyAppication get() {
        return instance;
    }

    public static BaseComponent getBaseComponent() {
        return baseComponent;
    }
}
