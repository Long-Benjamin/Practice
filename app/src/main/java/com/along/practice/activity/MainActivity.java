package com.along.practice.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.along.practice.R;
import com.along.practice.activity.adapter.MeiZhiAdapter;
import com.along.practice.bean.GankBean;
import com.along.practice.bean.MeizhiBean;
import com.along.practice.common.C;
import com.along.practice.http.RequestApi;
import com.along.practice.module.base.BaseActivity;
import com.along.practice.utils.LogUtil;
import com.along.practice.utils.NetworkUtils;
import com.along.practice.utils.SpUtil;
import com.along.practice.utils.ToastUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity implements MeiZhiAdapter.OnItemClickListener {

    public static final String TAG_EXIT = "Eixt_App";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.meizhi_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.text_error)
    TextView mError;

    private MeiZhiAdapter mMeiZhiAdapter;

    private int mPage = 1;
    int[] colors = {R.color.color_refresf_1, R.color.color_refresf_2, R.color.color_refresf_3, R.color.color_refresf_4};
    private int mType = -1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        //调整SwipeRefreshLayout的位置
        mSwipeRefresh.setColorSchemeResources(colors);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPage = 1;
                initData();
            }
        });
        initData();
        mSwipeRefresh.setRefreshing(true);

        mMeiZhiAdapter = new MeiZhiAdapter(MainActivity.this);
        mMeiZhiAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMeiZhiAdapter);
        setOnScrollChangeListener(recyclerView,layoutManager);

    }

    private void setOnScrollChangeListener(RecyclerView pRecyclerView,LinearLayoutManager layoutManager) {

        pRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
               if (newState == RecyclerView.SCROLL_STATE_IDLE
                       && lastVisibleItem + 1 == mMeiZhiAdapter.getItemCount()) {
                   mSwipeRefresh.setRefreshing(true);
                   mPage +=1;
                   initData();
}
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            }
        });

    }

    @Override
    protected void initNavigationBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("首页");
    }

    /**
     * 数据请求
     */
    private void initData() {
        RequestApi.getMeizhiList(mPage).subscribe(mOberver);

    }


    Observer<GankBean> mOberver = new Observer<GankBean>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull GankBean gankBean) {
            if (gankBean != null && gankBean.results.size() > 0){
                if (mPage == 1)mMeiZhiAdapter.cleanMeiZhies();
                mMeiZhiAdapter.addMeiZhies(gankBean.results);
                mMeiZhiAdapter.notifyDataSetChanged();
                if (mPage == 1 && mMeiZhiAdapter.getItemCount() == 0)mError.setText("暂没有找到妹纸 TT");

            }else {
                if (mPage ==1) {
                    mError.setVisibility(View.VISIBLE);
                    mError.setText("没有找到妹纸 TT");
                }else {
                    ToastUtils.showToast(MainActivity.this,"你已经找到了全部妹纸！");
                }
            }

        }

        @Override
        public void onError(@NonNull Throwable e) {
            mSwipeRefresh.setRefreshing(false);
            mError.setText("暂没找到妹纸，稍后再试吧！");
        }

        @Override
        public void onComplete() {
            mSwipeRefresh.setRefreshing(false);
        }
    };

    @Override
    public void onItemClick(View view, int position) {
        Intent vIntent = new Intent(MainActivity.this, PhotoShowActivity.class);
        vIntent.putExtra(C.Intents.PHOTO_POSITION, position);
        vIntent.putStringArrayListExtra(C.Intents.PHOTO_LIST,  mMeiZhiAdapter.getUrls());
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        vIntent.putExtra(C.Intents.PHOTO_LOCATIONX, location[0]);//必须
        vIntent.putExtra(C.Intents.PHOTO_LOCATIONY, location[1]);//必须
        vIntent.putExtra(C.Intents.PHOTO_WIDTH, view.getWidth());//必须
        vIntent.putExtra(C.Intents.PHOTO_HEIGHT, view.getHeight());//必须
        startActivity(vIntent);
        overridePendingTransition(0, 0);
    }
}
