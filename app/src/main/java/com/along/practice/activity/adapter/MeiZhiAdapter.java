package com.along.practice.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.along.practice.R;
import com.along.practice.activity.MainActivity;
import com.along.practice.activity.NomalActivity;
import com.along.practice.bean.MeizhiBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longj on 2017/6/15.
 * E-mail: longjintang123@163.com
 */

public    class MeiZhiAdapter extends Adapter<MeiZhiAdapter.MyViewHolder>{

    private Context mCont;
    public List<MeizhiBean> mMeiZhies = new ArrayList<>();

    public MeiZhiAdapter (Context pCont){

        this.mCont = pCont;
    }


    @Override
    public MeiZhiAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mCont)
                .inflate(R.layout.recycle_item_card, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText("成鱼落雁，毕业绣花(" + mMeiZhies.get(position).createdAt+")");
        Glide.with(mCont)
                .load(mMeiZhies.get(position).url)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(holder.imgview);
        holder.cardview.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCont.startActivity(new Intent(mCont, NomalActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeiZhies.size();
    }

    public void addMeiZhies(List<MeizhiBean> pMeiZhies){
        mMeiZhies.addAll(pMeiZhies);
    }

    public void cleanMeiZhies(){
        mMeiZhies.clear();
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
