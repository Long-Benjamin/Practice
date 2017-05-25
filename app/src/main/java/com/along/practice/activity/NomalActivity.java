package com.along.practice.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.along.practice.R;
import com.along.practice.module.base.BaseActivity;
import com.along.practice.utils.StatusBarUtil;
import com.along.zxinglibrary.utils.QRCodeUtil;
import com.along.zxinglibrary.zxing.activity.CaptureActivity;

import butterknife.BindView;

import static com.along.zxinglibrary.zxing.activity.CaptureActivity.RESULT_CODE_QR_SCAN;

public class NomalActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_view)
    ImageView imageView1;
    @BindView(R.id.image_view2)
    ImageView imageView2;
    @BindView(R.id.tv_code)
    TextView mTVcode;
    @BindView(R.id.scan_code)
    Button mScancode;


    @Override
    protected void initView(Bundle savedInstanceState) {

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
        });

        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        imageView1.setImageBitmap(
                QRCodeUtil.createQRImage("就是我",500,bmp));
        imageView2.setImageBitmap(
                QRCodeUtil.createQRCodeBitmap("就是我",200,200));
    }

    @Override
    protected void initNavigationBar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nomal;
    }

    @Override
    public boolean isOpen(){

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mScancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(NomalActivity.this, CaptureActivity.class), 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_CODE_QR_SCAN){
            String code = data.getExtras().getString("qr_scan_result");
            mTVcode.setText(code);
        }
    }
}
