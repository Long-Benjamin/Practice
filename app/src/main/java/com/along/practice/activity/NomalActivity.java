package com.along.practice.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.along.practice.R;
import com.along.practice.module.base.BaseActivity;
import com.along.practice.utils.StatusBarUtil;
import com.along.zxinglibrary.QRCodeUtil;

import butterknife.BindView;

public class NomalActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_view)
    ImageView imageView1;
    @BindView(R.id.image_view2)
    ImageView imageView2;


    @Override
    protected void initView(Bundle savedInstanceState) {

        setSupportActionBar(toolbar);

        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        imageView1.setImageBitmap(
                QRCodeUtil.createQRImage("龙金堂就是我",500,bmp));
        imageView2.setImageBitmap(
                QRCodeUtil.createQRCodeBitmap("龙金堂就是我",200,200));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nomal;
    }

    @Override
    public boolean isOpen(){

        return true;
    }

}
