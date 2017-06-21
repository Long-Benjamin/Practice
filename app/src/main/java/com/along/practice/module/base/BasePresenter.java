package com.along.practice.module.base;

/**
 * Created by longj on 2017/5/14.
 * E-mail: longjintang123@163.com
 */

public abstract class BasePresenter<M,T> {

    public M mModel;
    public T mView;

    public void attachVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
        mView = null;
        mModel = null;
    }

    public abstract void onStart();

}
