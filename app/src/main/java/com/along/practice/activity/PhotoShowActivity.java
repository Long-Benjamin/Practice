package com.along.practice.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.along.practice.R;
import com.along.practice.activity.adapter.ViewPagerAdapter;
import com.along.practice.common.C;
import com.along.practice.module.base.BaseActivity;
import com.along.practice.utils.display.ImageLoader;
import com.along.practice.widget.MZViewPager;
import com.along.practice.widget.SmoothImageView;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *  This is a activity for picture show,
 *  realize some transition animtion effect like weixin photo preview
 *
 */
public class PhotoShowActivity extends BaseActivity {

    @BindView(R.id.meizhi_photo_viewPage)
    MZViewPager mViewpager;
    @BindView(R.id.meizhi_position)
    TextView mPoiView;
    @BindView(R.id.meizhi_show_image)
    SmoothImageView mImgView;

    private int mPosition;
    private ArrayList<String> mUrls = new ArrayList<>();
    private ArrayList<PhotoView> mViews = new ArrayList<>();
    private boolean isDestroyed = false;

    @Override
    protected void initView(Bundle savedInstanceState) {

        initDatas();

        initViews();

    }

    @Override
    protected void initNavigationBar() {
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_show;
    }

    private void initDatas() {
        mUrls = getIntent().getStringArrayListExtra(C.Intents.PHOTO_LIST);
        mPosition = getIntent().getIntExtra(C.Intents.PHOTO_POSITION, 0);
        int mLocationX = getIntent().getIntExtra("locationX", 0);
        int mLocationY = getIntent().getIntExtra("locationY", 0);
        int mWidth = getIntent().getIntExtra("width", 0);
        int mHeight = getIntent().getIntExtra("height", 0);

        mImgView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        mImgView.transformIn();
        mImgView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mImgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.getInstance().displayImage(this,mUrls.get(mPosition), mImgView);
    }

    private void initViews() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT);
        for (int i=0; i<4; i++){
            PhotoView view = new PhotoView(this);
            view.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setBackgroundColor(getResources().getColor(R.color.common_white,null));
            }else {
                view.setBackgroundColor(getResources().getColor(R.color.common_white));
            }
            mViews.add(view);
        }

        mPoiView.setText(String.format("%1$s/%2$s",mPosition+1, mUrls.size()));

        mViewpager.setAdapter(new ViewPagerAdapter(PhotoShowActivity.this, mViews, mUrls));
        mViewpager.setCurrentItem(mPosition);

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPoiView.setText(String.format("%1$s/%2$s",position+1, mUrls.size()));

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isDestroyed) return;
                        if (mImgView != null && mImgView.getVisibility() == View.VISIBLE) {
                            mImgView.setVisibility(View.GONE);
                            if (mViewpager != null) mViewpager.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        }).start();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(0, R.anim.exit_out);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }
}
