package com.along.practice.http;

import com.along.practice.bean.GankBean;
import com.along.practice.bean.User;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by longj on 2017/6/8.
 * E-mail: longjintang123@163.com
 */

public    class RequestApi   {

    public static void getName(int id){
        Retrofactory.getApiServer().groupList(id);
    }

    public static Observable<GankBean> getMeizhiList(int page){
       return Retrofactory.getGankServer().getMeizhiData(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




}
