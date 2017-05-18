package com.along.practice.module.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.along.practice.Manager.ActivitiesManager;
import com.along.practice.utils.LogUtil;
import com.along.practice.utils.StatusBarUtil;
import com.along.practice.utils.ToastUtils;
import com.along.practice.widget.swipbacklayout.SwipeBackActivity;
import com.along.practice.widget.swipbacklayout.SwipeBackLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends SwipeBackActivity {

    protected String TAG = "";
    protected Context mContext;
    protected AppCompatActivity mActivity;

    Unbinder binder;
    private boolean isOpen = false;//侧滑退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        StatusBarUtil.setTransparent(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);

        LogUtil.e(TAG,"onCreate()");
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();

        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        mContext = this;

        setSwipeBackEnable(isOpen());
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        initView(savedInstanceState);
        mActivity = this;
        ActivitiesManager.getActivitiesManager().addActivity(this);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
            super.setContentView(layoutResID);
    }

    public boolean isOpen() {
        return isOpen;
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e(TAG,"onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e(TAG,"onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e(TAG,"onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e(TAG,"onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG,"onDestroy()");

        ActivitiesManager.getActivitiesManager().finishActivity(this);
        if (binder != null) binder.unbind();
    }

    /**
     *  用于主题模式切换
     */
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }


    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }
}