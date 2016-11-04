package com.zbq.android.googleplay.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.UI.holder.DetailDesHolder;
import com.zbq.android.googleplay.UI.holder.DetailDownloadHolder;
import com.zbq.android.googleplay.UI.holder.DetailPicHolder;
import com.zbq.android.googleplay.UI.holder.DetailSafeHolder;
import com.zbq.android.googleplay.UI.holder.HomeDetailAppInfoHolder;
import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.http.protocol.HomeDetailProtocol;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/11/2.
 */

public class HomeDetailActivity extends BaseActivity {

    private LoadingPage mLoadingPager;
    private String mPackageName;
    private FrameLayout fl_detail_appInfo, fl_detail_safe, fl_detail_des,fl_detail_download;
    private AppInfo mData;
    private HorizontalScrollView hsv_detail_pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPackageName = getIntent().getStringExtra("packageName");

        mLoadingPager = new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };

        setContentView(mLoadingPager);
        mLoadingPager.loadData();
        initActionbar();
    }

    public View onCreateSuccessView() {
        View view = UIUtils.inflate(R.layout.page_home_detail);
        fl_detail_appInfo = (FrameLayout) view.findViewById(R.id.fl_detail_appInfo);
        fl_detail_safe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        fl_detail_des = (FrameLayout) view.findViewById(R.id.fl_detail_des);
        hsv_detail_pic = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pic);
        fl_detail_download = (FrameLayout) view.findViewById(R.id.fl_detail_download);

        HomeDetailAppInfoHolder homeDetailAppInfoHolder = new HomeDetailAppInfoHolder();
        fl_detail_appInfo.addView(homeDetailAppInfoHolder.getRootView());
        homeDetailAppInfoHolder.setData(mData);

        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        fl_detail_safe.addView(detailSafeHolder.getRootView());
        detailSafeHolder.setData(mData);

        DetailPicHolder detailPicHolder = new DetailPicHolder();
        hsv_detail_pic.addView(detailPicHolder.getRootView());
        detailPicHolder.setData(mData);

        DetailDesHolder detailDesHolder = new DetailDesHolder();
        fl_detail_des.addView(detailDesHolder.getRootView());
        detailDesHolder.setData(mData);

        DetailDownloadHolder detailDownloadHolder = new DetailDownloadHolder();
        fl_detail_download.addView(detailDownloadHolder.getRootView());
        detailDownloadHolder.setData(mData);
        return view;
    }

    public LoadingPage.ResultState onLoad() {
        HomeDetailProtocol homeDetailProtocol = new HomeDetailProtocol(mPackageName);
        mData = homeDetailProtocol.getData(0);
        if (mData != null) {
            Log.d("STATE_SUCCESS", "onLoad: ");
            return LoadingPage.ResultState.STATE_SUCCESS;

        } else {
            Log.d("STATE_ERROR", "onLoad: ");
            return LoadingPage.ResultState.STATE_ERROR;
        }

    }

    public void initActionbar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//返回左上角返回键
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
