package com.zbq.android.googleplay.UI.holder;

import android.view.View;

/**
 * Created by zhangbingquan on 2016/10/28.
 */

public abstract class BaseHolder<T> {


    private T data;
    private final View mRootView;

    public BaseHolder() {
        mRootView = initView();
        mRootView.setTag(this);
    }

    public abstract View initView();

    public View getRootView() {
        return mRootView;
    }

    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }

    public T getData() {
        return data;
    }

    public abstract void refreshView(T data);

}
