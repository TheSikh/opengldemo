package com.gagan.opengl.demo1;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gagan.opengl.demo1.graphics.CustomGLES2View;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private CustomGLES2View customGLES2View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();

        if (deviceConfigurationInfo.reqGlEsVersion < 0x00020000) {
            Toast.makeText(this, "App Requires OpenGles Version 0x00020000 or above", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.d(TAG, "OpenGles Version " + deviceConfigurationInfo.getGlEsVersion() + "found");
        }

        customGLES2View = new CustomGLES2View(this);

        setContentView(customGLES2View);

    }

    @Override
    protected void onResume() {
        super.onResume();
        customGLES2View.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        customGLES2View.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
