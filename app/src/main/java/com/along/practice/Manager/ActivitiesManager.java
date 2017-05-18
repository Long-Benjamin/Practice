package com.along.practice.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.along.practice.activity.MainActivity;

import java.util.Stack;

/**
 * Created by longj on 2017/5/13.
 */

public class ActivitiesManager   {

    private static ActivitiesManager activitiesManager;
    private static Stack<Activity> activities;

   private ActivitiesManager(){};

    public static ActivitiesManager getActivitiesManager(){
        if (activitiesManager == null){
            activitiesManager = new ActivitiesManager();
        }
        return activitiesManager;
    }

    public void addActivity(Activity activity){
        if (activities == null){
            activities = new Stack<Activity>();
        }
        activities.add(activity);
    }

    public Activity getCurrentActivity(){
        Activity activity = null;
        if (activities != null && !activities.empty() ){
            activity = activities.lastElement();
        }
        return activity;
    }

    public void finishActivity(Activity activity){
        if (activity != null){
            activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activities) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activities.size(); i < size; i++) {
            if (null != activities.get(i)) {
                activities.get(i).finish();
            }
        }
        activities.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {

        finishAllActivity();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.TAG_EXIT, true);
        context.startActivity(intent);

        /*try {
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }*/
    }

    public boolean isAppExit() {
        return activities == null || activities.isEmpty();
    }

}
