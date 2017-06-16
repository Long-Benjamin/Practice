package com.along.practice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.along.practice.R;
import com.along.practice.activity.adapter.MeiZhiAdapter;
import com.along.practice.bean.GankBean;
import com.along.practice.bean.MeizhiBean;
import com.along.practice.http.RequestApi;
import com.along.practice.module.base.BaseActivity;
import com.along.practice.utils.LogUtil;
import com.along.practice.utils.SpUtil;
import com.along.practice.utils.ToastUtils;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    public static final String TAG_EXIT = "Eixt_App";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public List<MeizhiBean> mMeiZhies = new ArrayList<>();
    private int mPage = 1;
    private MeiZhiAdapter mMeiZhiAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        toolbar.setTitle("首页");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpUtil.getNightModel(MainActivity.this)){
                    SpUtil.setNightModel(mContext, false);
                }else {
                    SpUtil.setNightModel(mContext, true);
                }
                MainActivity.this.reload();

            }
        });
        RequestApi.getMeizhiList(mPage).subscribe(mOberver);

        mMeiZhiAdapter = new MeiZhiAdapter(MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMeiZhiAdapter);
    }

    @Override
    protected void initNavigationBar() {

    }


    Observer<GankBean> mOberver = new Observer<GankBean>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull GankBean gankBean) {
            LogUtil.error("請求下來了"+gankBean.results.size()+"個妹子");
            if (gankBean != null && gankBean.results.size() > 0){
                if (mPage == 1)mMeiZhiAdapter.cleanMeiZhies();
                mMeiZhies.addAll(gankBean.results);
                mMeiZhiAdapter.addMeiZhies(mMeiZhies);
                mMeiZhiAdapter.notifyDataSetChanged();

            }else {
                ToastUtils.showToast(MainActivity.this,"1没有妹纸了！");
            }

        }

        @Override
        public void onError(@NonNull Throwable e) {
            ToastUtils.showToast(MainActivity.this,"2没有妹纸了！");
        }

        @Override
        public void onComplete() {

        }
    };
}
