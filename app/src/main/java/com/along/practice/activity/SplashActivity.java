package com.along.practice.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.support.v8.renderscript.*;
import com.along.practice.R;
import com.qiushui.blurredview.BlurredView;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    static final int[] mPictrues = {R.drawable.timg1,R.drawable.timg2,R.drawable.timg3,R.drawable.timg4};
    private BlurredView mImgView;
    private int mLevel = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (mLevel < 0)mLevel = 0;
                    if (mLevel > 85)mLevel = 85;
                    mImgView.setBlurredLevel(mLevel);
                    mLevel += 1;
                    mHandler.sendEmptyMessageDelayed(0,15);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

    }

    private void initView() {
        mImgView = (BlurredView) findViewById(R.id.splash_imgview);

        Random random = new Random();
        int vId = random.nextInt(4)%(4);
        mImgView.setBlurredImg(getResources().getDrawable(mPictrues[vId]));

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );

        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mHandler.sendEmptyMessageDelayed(0,200);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //在这里做一些初始化的操作
                //跳转到指定的Activity
                mHandler.removeMessages(0);
                startActivity();
            }


            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mImgView.startAnimation(scaleAnim);

    }

    private void startActivity() {
        Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }



}
