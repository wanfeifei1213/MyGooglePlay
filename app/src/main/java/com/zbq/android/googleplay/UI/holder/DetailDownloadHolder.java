package com.zbq.android.googleplay.UI.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.UI.view.ProgressHorizontal;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.domain.DownloadInfo;
import com.zbq.android.googleplay.manager.DownloadManager;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/11/4.
 */

public class DetailDownloadHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObserver, View.OnClickListener {
    private Button btn_fav;
    private Button btn_share;
    private Button btn_download;
    private FrameLayout fl_progress;
    private DownloadManager mDownloadManager;
    private int mCurrentState;
    private float mProgress;

    private ProgressHorizontal mProgressHorizontal;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);
        fl_progress = (FrameLayout) view.findViewById(R.id.fl_progress);
        mProgressHorizontal = new ProgressHorizontal(UIUtils.getContext());
        mProgressHorizontal.setProgressBackgroundResource(R.drawable.progress_bg);
        mProgressHorizontal.setProgressResource(R.drawable.progress_normal);
        mProgressHorizontal.setProgressTextColor(Color.WHITE);
        mProgressHorizontal.setProgressTextSize(UIUtils.dip2px(18));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        fl_progress.addView(mProgressHorizontal, params);

        btn_download = (Button) view.findViewById(R.id.btn_download);
        btn_share = (Button) view.findViewById(R.id.btn_share);
        btn_fav = (Button) view.findViewById(R.id.btn_fav);

        btn_download.setOnClickListener(this);
        fl_progress.setOnClickListener(this);

        mDownloadManager = DownloadManager.getInstance();
        mDownloadManager.registerObserver(this);

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(data);
        if (downloadInfo != null) {
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        } else {
            mCurrentState = DownloadManager.STATE_UNDO;
            mProgress = 0;
        }
        refreshUI(mCurrentState,mProgress);
    }

    private void refreshUI(int currentState ,float progress) {
        mCurrentState = currentState;
        mProgress = progress;
        switch (mCurrentState) {
            case DownloadManager.STATE_UNDO:
                fl_progress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("下载");
                break;
            case DownloadManager.STATE_WAITING:
                fl_progress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("等待中…");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                fl_progress.setVisibility(View.VISIBLE);
                btn_download.setVisibility(View.GONE);
                mProgressHorizontal.setCenterText("");
                mProgressHorizontal.setProgress(mProgress);
                break;
            case DownloadManager.STATE_PAUSE:
                fl_progress.setVisibility(View.VISIBLE);
                btn_download.setVisibility(View.GONE);
                mProgressHorizontal.setCenterText("暂停");
                mProgressHorizontal.setProgress(mProgress);
                break;
            case DownloadManager.STATE_ERROR:
                fl_progress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("下载失败");
                break;
            case DownloadManager.STATE_SUCCESS:
                fl_progress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("安装");
                break;
            default:
                break;
        }
    }

    private void refreshUIOnMainThread(final DownloadInfo downloadInfo) {
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(downloadInfo.currentState,downloadInfo.getProgress());
            }
        });
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
//        AppInfo appInfo = getData();
//        if (appInfo.id.equals(info.id)) {
            refreshUIOnMainThread(info);
//        }

    }

    @Override
    public void onDownloadProgressChanged(DownloadInfo info) {
//        AppInfo appInfo = getData();
//        if (appInfo.id.equals(info.id)) {
            refreshUIOnMainThread(info);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
            case R.id.fl_progress:
                //根据状态决定下一步操作
                if (mCurrentState == DownloadManager.STATE_UNDO
                        || mCurrentState == DownloadManager.STATE_PAUSE
                        || mCurrentState == DownloadManager.STATE_ERROR) {
                    mDownloadManager.download(getData());
                } else if (mCurrentState == DownloadManager.STATE_DOWNLOADING
                        || mCurrentState == DownloadManager.STATE_WAITING) {
                    mDownloadManager.pause(getData());
                } else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
                    mDownloadManager.install(getData());
                }
                break;
            default:
                break;
        }
    }
}
