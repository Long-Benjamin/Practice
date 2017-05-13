package com.along.practice;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by longj on 2017/5/13.
 */

public class MainApplication extends Application {

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    private static MainApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = MainApplication.this;
        getScreenSize();

    }

    public static synchronized MainApplication getInstance(){
        return mInstance;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

    public void getScreenSize() {

        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = manager.getDefaultDisplay();
        display.getMetrics(metrics);

        DIMEN_RATE = metrics.density / 1.0F;
        DIMEN_DPI = metrics.densityDpi;
        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;

        if (SCREEN_WIDTH > SCREEN_HEIGHT){
            int size = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = size;
        }
    }
    }
