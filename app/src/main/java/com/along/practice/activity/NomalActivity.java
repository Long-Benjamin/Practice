package com.along.practice.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.along.practice.R;
import com.along.practice.module.base.BaseActivity;
import com.along.practice.utils.StatusBarUtil;
import com.along.practice.utils.ToastUtils;
import com.along.zxinglibrary.utils.QRCodeUtil;
import com.along.zxinglibrary.zxing.activity.CaptureActivity;
import com.along.zxinglibrary.zxing.encoding.EncodingHandler;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.along.zxinglibrary.zxing.activity.CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN;
import static com.along.zxinglibrary.zxing.activity.CaptureActivity.RESULT_CODE_QR_SCAN;

public class NomalActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.image_view)
    ImageView mImageView1;
    @BindView(R.id.image_view2)
    ImageView mImageView2;
    @BindView(R.id.imgview_qrcode)
    ImageView mIVqrcode;
    @BindView(R.id.tv_code)
    TextView mTVcode;
    @BindView(R.id.scan_code)
    Button mBTscan;
    @BindView(R.id.text_input_key)
    EditText mETkeyinput;
    @BindView(R.id.text_input_layout)
    TextInputLayout mTextInputLayout;


    @Override
    protected void initView(Bundle savedInstanceState) {
        //设置状态栏透明
        StatusBarUtil.setTransparent(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.arrow_back_white);
        mToolbar.setNavigationOnClickListener(v -> {
            this.finish();
        });

        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        //设置可以计数
        mTextInputLayout.setCounterEnabled(true);
        //计数的最大值
        mTextInputLayout.setCounterMaxLength(25);

        mETkeyinput.setOnKeyListener((v, keyCode, event) ->  {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    String keyWords = mETkeyinput.getText().toString().trim();
                    //带不带logo？
                    mImageView1.setImageBitmap(EncodingHandler.createQRCode(keyWords, 200,200, null));
                    mImageView2.setImageBitmap(EncodingHandler.createQRCode(keyWords, 200, 200, bmp));

                    return true;
                }

                return false;

            });
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

        mBTscan.setOnClickListener(v ->

                new RxPermissions(NomalActivity.this)
                .request(Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {

                    if (aBoolean) {
                        startActivityForResult(new Intent(NomalActivity.this, CaptureActivity.class), 1000);

                    }  else {
                        ToastUtils.showToast(NomalActivity.this,"不给摄像权限，我怎么帮你识别二维码呢！");
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_CODE_QR_SCAN){
            String code = data.getExtras().getString(INTENT_EXTRA_KEY_QR_SCAN);
            mTVcode.setText("扫描结果："+code);
             byte[] bis = data.getByteArrayExtra("bitmap");
            if (bis.length > 0){
                Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
                mIVqrcode.setImageBitmap(bitmap);
            }
//            bitmap.recycle();
        }
    }
}
