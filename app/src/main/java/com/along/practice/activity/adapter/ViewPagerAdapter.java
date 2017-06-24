package com.along.practice.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.along.practice.activity.NomalActivity;
import com.along.practice.utils.display.ImageLoader;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ljt on 2016/11/30.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<PhotoView> mViews = new ArrayList<>();
    private ArrayList<String> mUrls = new ArrayList<>();

    public ViewPagerAdapter(Context pContextint, ArrayList<PhotoView> pViews, ArrayList<String> pUrls ) {
        this.mViews = pViews;
        this.mUrls = pUrls;
        this.mContext = pContextint;
    }

    @Override
    public int getCount(){
        return mUrls.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object){
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        int i = position % 4;
        PhotoView photoView = mViews.get(i);
        GlideBitmapDrawable bitmapDrawable = (GlideBitmapDrawable) photoView.getDrawable();
        if (bitmapDrawable != null) {
            Bitmap bm = bitmapDrawable.getBitmap();
            if (bm!=null && !bm.isRecycled()) {
                Log.d("...desimg..", "被回收了" + bm.getByteCount());
                photoView.setImageResource(0);
//                bm.recycle();//待找到图片被回收后回看抛异常问题
            }
        }

        container.removeView(photoView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        /** 循环使用这4个View */
        int i = position % 4;
        final PhotoView photoView = mViews.get(i);

        ImageLoader.getInstance().displayImage(mContext,mUrls.get(position),photoView);
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImageLoader.getInstance().downloadPicture(mContext, mUrls.get(position), new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                        try {
                            savaFileToSD("meizhi"+ SystemClock.currentThreadTimeMillis()+".jpg",resource);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return false;
            }
        });
        container.addView(photoView);
        return photoView;
    }

    //往SD卡写入文件的方法
    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
         //如果手机已插入sd卡,且app具有读写sd卡的权限
         if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/meizhi";
                File dir1 = new File(filePath);
                if (!dir1.exists()){
                    dir1.mkdirs();
                }
                filename = filePath+ "/" + filename;
                //这里就不要用openFileOutput了,那个是往手机内存中写数据的
                FileOutputStream output = new FileOutputStream(filename);
                output.write(bytes);
                //将bytes写入到输出流中
                output.close();
                //关闭输出流
             Toast.makeText(mContext, "图片已成功保存到"+filePath, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(mContext, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }

}
