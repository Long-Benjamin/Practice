package com.along.practice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.along.practice.module.base.BaseActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public static final String TAG_EXIT = "Eixt_App";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MyViewHolder holder = new MyViewHolder(LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.recycle_item_card, parent, false));
                return holder;

            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.tv.setText("成鱼落雁，毕业绣花+" + position);
                Glide.with(MainActivity.this)
                        .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494940207647&di=a73f40055eab87352c66d56854d38304&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F72f082025aafa40fe871b36bad64034f79f019d4.jpg")
                        .centerCrop()
                        .error(R.mipmap.ic_launcher)
                        .into(holder.imgview);
                holder.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, NomalActivity.class));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return px
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView imgview;
        CardView cardview;

        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.name);
            imgview = (ImageView) view.findViewById(R.id.image_view);
            cardview = (CardView) view.findViewById(R.id.card_view);
        }

    }
}
