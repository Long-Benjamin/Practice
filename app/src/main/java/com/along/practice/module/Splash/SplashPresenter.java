package com.along.practice.module.Splash;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.along.practice.R;

import java.util.Random;

/**
 * Created by longj on 2017/5/14.
 * E-mail: longjintang123@163.com
 */

public class SplashPresenter {

    static final int[] mPictrues = {R.drawable.timg1,R.drawable.timg2,R.drawable.timg3,R.drawable.timg4};

    Context mCtx;
    ISplashView mSplashView;
    private int mLevel = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (mLevel < 0)mLevel = 0;
                    if (mLevel > 85)mLevel = 85;
                    mSplashView.setImageAlpha(mLevel);
                    mLevel += 1;
                    mHandler.sendEmptyMessageDelayed(0,15);
                    break;
                default:
                    break;
            }
        }
    };

    public SplashPresenter(ISplashView pSplashView, Context pCtx){
        this.mSplashView = pSplashView;
        this.mCtx = pCtx;
    }

    public void setCopyrightText(String str){

        mSplashView.setCopyrightText(str);
    }

    public void showImage(){

        Random random = new Random();
        int vId = random.nextInt(4)%(4);
        mSplashView.showImage(mCtx.getResources().getDrawable(mPictrues[vId]));
    }

    public void setImageAnimation(){
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
                mSplashView.toMainActivity();
            }


            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mSplashView.setImageAnimation(scaleAnim);
    }


}
