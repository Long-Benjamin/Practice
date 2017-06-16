package com.along.practice.http.api;

import com.along.practice.bean.GankBean;
import com.along.practice.bean.User;
import com.along.practice.common.C;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by longj on 2017/6/9.
 * E-mail: longjintang123@163.com
 */

public    interface GankServer {


    @GET("data/福利/" + C.Base.PAGE_SIZE + "/{page}")
    Observable<GankBean> getMeizhiData(@Path("page") int page);

}
