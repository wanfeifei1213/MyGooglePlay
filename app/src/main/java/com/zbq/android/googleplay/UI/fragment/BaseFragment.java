package com.zbq.android.googleplay.UI.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView textView = new TextView(getContext());
//        textView.setText(getClass().getSimpleName());
        mLoadingPage = new LoadingPage(UIUtils.getContext()){

            @Override
            public View onCreateSuccessView() {
//                return onCreateSuccessView(); 要调用BaseFragment里的onCreateSuccessView()
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return  BaseFragment.this.onLoad();
            }
        };
        return mLoadingPage;
    }
    public abstract View onCreateSuccessView();
    public abstract LoadingPage.ResultState onLoad();
    public void loadData(){
        if (mLoadingPage!=null){
            mLoadingPage.loadData();
        }
    }
    public LoadingPage.ResultState check(Object obj){
        if (obj!=null){
            Log.d("check", "check: "+obj.toString());
            if (obj instanceof ArrayList){
                ArrayList list =(ArrayList) obj;
                if (list.isEmpty()){
                    return  LoadingPage.ResultState.STATE_EMPTY;
                }else {
                    return  LoadingPage.ResultState.STATE_SUCCESS;
                }

            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
