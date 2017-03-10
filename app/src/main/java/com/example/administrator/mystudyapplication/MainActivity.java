package com.example.administrator.mystudyapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lib_hybird.JsCallJava;
import com.example.lib_navigator.ActivityEvent;
import com.example.lib_navigator.ActivityEventConnection;
import com.example.module_one.MainOneActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

@ActivityEventConnection(connect = ActivityEvent.Connect.MainActivity)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainOneActivity";
    int index;
    @Inject
    Student student;
    @Inject
    MyAppication context;
    private WebView webView;
    private TextView hello;
    private JsCallJava mJsCallJava;
    private ExpandIconView expandIconView3;
    private Scene scene1;
    private Scene scene2;
    private boolean isScene;

    @SuppressLint("AddJavascriptInterface")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StudentComponent component = DaggerStudentComponent.builder().studentModule(new StudentModule()).baseComponent(MyAppication.getBaseComponent()).build();
        component.inject(this);
        Log.d(TAG, "onCreate: " + student.getName());

        Log.d(TAG, "onCreate: " + (context == null));

        hello = (TextView) findViewById(R.id.id_hello);
        webView = (WebView) findViewById(R.id.id_webView);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url) && url.contains("asher")) {
                    Log.d(TAG, "shouldOverrideUrlLoading: " + Thread.currentThread().getName());
                    Toast.makeText(MainActivity.this, "intercept", Toast.LENGTH_SHORT).show();
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        mJsCallJava = new JsCallJava();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(MainActivity.this, "onJsAlert", Toast.LENGTH_SHORT).show();
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Toast.makeText(MainActivity.this, "onJsConfirm", Toast.LENGTH_SHORT).show();
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Toast.makeText(MainActivity.this, "onJsPrompt", Toast.LENGTH_SHORT).show();
                result.confirm(mJsCallJava.call(view, message));
                return true;
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(TAG, "onConsoleMessage: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/html/jsCode.html");
        webView.addJavascriptInterface(this, "android");

        hello.setText("Call JS method");
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 19) {
                    webView.evaluateJavascript("javascript:show_alert(" + index + ")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d(TAG, "onReceiveValue: " + Thread.currentThread().getName());
                            Log.d(TAG, "onReceiveValue: " + value);
                        }
                    });
                } else {
                    webView.loadUrl("javascript:show_alert(" + index + ")");
                }

                index++;
            }
        });


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hello");
//                subscriber.onNext("World");
//                subscriber.onCompleted();
//            }
//        });
        Observable<String> observable = Observable.just("Hello", "World");
        String[] array = {"Hello2", "World2"};
        Observable<String> observable2 = Observable.from(array);
        observable2.subscribe(observer);

        Log.d(TAG, "onCreate: " + String.format(
                "Unexpected char %#04x", (int) '嫏'));
        expandIconView3 = (ExpandIconView) findViewById(R.id.expand_icon3);
        findViewById(R.id.js).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainOneActivity.class));
            }
        });
        expandIconView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandIconView3.switchState();
            }
        });

        addShortcut();

//        ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.scene_container);
//        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_one, this);
//        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_two, this);
//        TransitionManager.go(scene1);
    }

    @JavascriptInterface
    public void jsCallAndroid() {
        Log.d(TAG, "jsCallAndroid: " + Thread.currentThread().getName());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: " + Thread.currentThread().getName());
                Toast.makeText(MainActivity.this, "js call android", Toast.LENGTH_SHORT).show();
                hello.setText("Success");
            }
        });
    }

    private void addShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1")
                    .setShortLabel("快捷方式")
                    .setLongLabel("快捷方式测试长标题显示")
                    .setIcon(Icon.createWithResource(context, R.drawable.ic_app_management_green_24))
                    .setIntent(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.baidu.com/")))
                    .build();

            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void change() {
        TransitionManager.go(isScene ? scene1 : scene2, new TransitionSet().addTransition(new ChangeBounds())
                .addTransition(new Fade(Fade.IN))
                .addTransition(new Fade(Fade.OUT)));
        isScene = !isScene;
    }

}
