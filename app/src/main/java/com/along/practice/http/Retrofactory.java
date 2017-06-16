package com.along.practice.http;

import android.util.Log;

import com.along.practice.MainApplication;
import com.along.practice.bean.User;
import com.along.practice.http.api.ApiServer;
import com.along.practice.http.api.GankServer;
import com.along.practice.http.api.ZhihuServer;
import com.along.practice.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by longj on 2017/6/8.
 * E-mail: longjintang123@163.com
 */

public  class Retrofactory {

    private static final String TAG = "Retrofactory";

    private static final String GANK_BASEURL = "";
    private static final String ZHIHU_BASEURL = "http://gank.io/api/";

    private static ApiServer apiServer;
    private static ZhihuServer zhihuServer;
    private static GankServer gankServer;

    private static HttpLoggingInterceptor getLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.error(message);

            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    /**
     * 获取OkHttpClient实例，单列模式
     * @return
     */
    private static OkHttpClient getSingleClient() {
        return SingletonHolder.singleClient;
    }
    private static class SingletonHolder {
        private static final OkHttpClient singleClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(getLoggingInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        long startTime = System.currentTimeMillis();
                        okhttp3.Response response = chain.proceed(chain.request());
                        long endTime = System.currentTimeMillis();
                        long duration=endTime-startTime;
                        okhttp3.MediaType mediaType = response.body().contentType();
                        String content = response.body().string();
                        Log.e(TAG,"\n");
                        Log.e(TAG,"----------Start----------------");
                        Log.e(TAG, "| "+request.toString());
                        String method=request.method();
                        if("POST".equals(method)){
                            StringBuilder sb = new StringBuilder();
                            if (request.body() instanceof FormBody) {
                                FormBody body = (FormBody) request.body();
                                for (int i = 0; i < body.size(); i++) {
                                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                                }
                                sb.delete(sb.length() - 1, sb.length());
                                Log.e(TAG, "| RequestParams:{"+sb.toString()+"}");
                            }
                        }
                        Log.e(TAG, "| Response:" + content);
                        Log.e(TAG,"----------End:"+duration+"毫秒----------");
                        return response.newBuilder()
                                .body(okhttp3.ResponseBody.create(mediaType, content))
                                .build();

                    }
                })
                .build();
//                .cache(new Cache(new File(MainApplication.getAppContext().getExternalCacheDir(), "http_cache"), 1024 * 1024 * 100))

    }

    /**
     * 获取Retrofit实例
     * @param baseUrl
     * @return
     */
    private static Retrofit getGankRetrofit(String baseUrl){
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getSingleClient())
                .build();
        return mRetrofit;

    }


    public static ApiServer getApiServer() {
        if (apiServer == null){
            apiServer = getGankRetrofit(GANK_BASEURL).create(ApiServer.class);
        }
        return apiServer;
    }

    public static ZhihuServer getZhihuServer() {
        if (zhihuServer == null){
            zhihuServer = getGankRetrofit(ZHIHU_BASEURL).create(ZhihuServer.class);
        }
        return zhihuServer;
    }

    public static GankServer getGankServer() {
        if (gankServer == null){
            gankServer = getGankRetrofit(ZHIHU_BASEURL).create(GankServer.class);
        }
        return gankServer;
    }


}
