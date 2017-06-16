package com.along.practice.http.api;

import com.along.practice.bean.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by longj on 2017/5/14.
 * E-mail: longjintang123@163.com
 */

public    interface ApiServer {

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId);

}
