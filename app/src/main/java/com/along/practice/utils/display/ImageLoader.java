package com.along.practice.utils.display;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * Created by ljt on 2016/10/13.
 */

public class ImageLoader {

    //Android把res/raw的资源转化为Uri形式访问(android.resource://)
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private ImageLoader (){}

    private static class ImageLoaderHolder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }

    public static final ImageLoader getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }


    //直接加载网络图片
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }


    //加载SD卡图片
    public void displayImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .centerCrop()
                .into(imageView);
    }

    //加载SD卡图片并设置大小
    public void displayImage(Context context, File file, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(file)
                .override(width, height)
                .centerCrop()
                .into(imageView);

    }

    //加载网络图片并设置大小
    public void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .override(width, height)
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片
    public void displayImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片
    public void displayImage(Context context, int resId, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .override(width, height)
                .crossFade()
                .into(imageView);
    }


    //加载drawable图片显示为圆形图片
    public void displayCricleImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    //加载网络图片显示为圆形图片
    public void displayCricleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                //.centerCrop()//网友反馈，设置此属性可能不起作用,在有些设备上可能会不能显示为圆形。
                .transform(new GlideCircleTransform(context))
                .crossFade()
                .into(imageView);
    }

    //加载SD卡图片显示为圆形图片
    public void displayCricleImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                //.centerCrop()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }


    /**
     *  //加载drawable图片显示为圆角图片
     *
     * @param context
     * @param resId
     * @param imageView
     * @param radius  圆角的半径
     * @param margin  边距
     */

    public void displayRoundedImage(Context context, int resId, ImageView imageView, int radius, int margin) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .bitmapTransform(new RoundedCornersTransformation(context, radius, margin,
                        RoundedCornersTransformation.CornerType.BOTTOM))
                .into(imageView);
    }

    //加载网络图片显示为圆角图片
    public void displayRoundedImage(Context context, String url, ImageView imageView, int radius, int margin) {
        Glide.with(context)
                .load(url)
                //.centerCrop()//网友反馈，设置此属性可能不起作用,在有些设备上可能会不能显示为圆形。
                .bitmapTransform(new RoundedCornersTransformation(context, radius, margin,
                        RoundedCornersTransformation.CornerType.BOTTOM))
                .crossFade()
                .into(imageView);
    }

    //加载SD卡图片显示为圆角图片
    public void displayRoundedImage(Context context, File file, ImageView imageView, int radius, int margin) {
        Glide.with(context)
                .load(file)
                //.centerCrop()
                .bitmapTransform(new RoundedCornersTransformation(context, radius, margin,
                        RoundedCornersTransformation.CornerType.BOTTOM))
                .into(imageView);
    }


    //加载GIF静态图片
    public void displayStaticGif(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .into(imageView);
    }

    //加载缩略图图片 thumbnail 缩略比
    public void displayThumbnail(Context context, String url, ImageView imageView, float thumbnail) {
        Glide.with(context)
                .load(url)
                .asGif()
                .thumbnail(thumbnail)
                .into(imageView);
    }

    //加载GIF图片，默认循环播放
    public void displayGif(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asGif()
                .into(imageView);
    }

    //播放视频文件
    public void displayVedio(Context context, String filePath, ImageView imageView) {
        Glide.with( context )
            .load( Uri.fromFile( new File( filePath ) ) )
            .into(imageView);
    }



    //将资源ID转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    public void downloadPicture(Context context, String url, SimpleTarget target) {
        Glide.with(context).load(url).asBitmap().toBytes().into(target);
    }

}
