package com.along.practice.module.Splash;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.along.practice.R;
import com.along.practice.activity.MainActivity;
import com.along.practice.module.base.BaseActivity;
import com.qiushui.blurredview.BlurredView;

import java.util.Random;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements ISplashView {

    @BindView(R.id.splash_imgview)
    BlurredView mImgView;
    @BindView(R.id.copy_textview)
    TextView mCopyrightTV;

    SplashPresenter presenter = new SplashPresenter(this, this) ;


    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter.setCopyrightText("");
        presenter.showImage();
        presenter.setImageAnimation();
    }

    @Override
    protected void initNavigationBar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


    @Override
    public void setCopyrightText(String copyrightText) {
//        mCopyrightTV.setText(copyrightText);
    }

    @Override
    public void showImage(Drawable drawable) {
        mImgView.setBlurredImg(drawable);

    }

    @Override
    public void setImageAnimation(ScaleAnimation scaleAnim) {
        mImgView.startAnimation(scaleAnim);
    }

    @Override
    public void setImageAlpha(int alpha) {
        mImgView.setBlurredLevel(alpha);
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
