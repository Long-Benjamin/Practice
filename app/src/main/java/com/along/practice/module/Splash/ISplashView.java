package com.along.practice.module.Splash;

import android.graphics.drawable.Drawable;
import android.view.animation.ScaleAnimation;

/**
 * Created by longj on 2017/5/14.
 * E-mail: longjintang123@163.com
 */

public interface ISplashView   {

    void setCopyrightText(String copyright);

    void showImage(Drawable drawable);

    void setImageAnimation(ScaleAnimation scaleAnim);

    void setImageAlpha(int alpha);

    void toMainActivity();
}
