package com.zbq.android.googleplay.UI.holder;

import android.graphics.Color;
import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.UI.view.ProgressArc;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.domain.DownloadInfo;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.manager.DownloadManager;
import com.zbq.android.googleplay.utils.BitmapHelper;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/10/28.
 */

public class HomeHolder extends BaseHolder<AppInfo> implements View.OnClickListener,DownloadManager.DownloadObserver {
    private BitmapUtils mBitmapUtils;
    private ImageView iv_icon;
    private TextView tv_name;
    private TextView tv_size;
    private RatingBar rb_star;
    private TextView tv_des;
    private FrameLayout fl_progress;
    private DownloadManager mDownloadManager;
    private ProgressArc mProgressArc;
    private int mCurrentState;
    private float mProgress;
    private TextView tvDownload;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_home);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        tvDownload = (TextView) view.findViewById(R.id.tv_download);
        mBitmapUtils= BitmapHelper.getBitmapUtils();

        fl_progress = (FrameLayout) view.findViewById(R.id.fl_progress);
        mProgressArc = new ProgressArc(UIUtils.getContext());
        mProgressArc.setArcDiameter(UIUtils.dip2px(26));
        mProgressArc.setProgressColor(Color.BLUE);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(UIUtils.dip2px(27), UIUtils.dip2px(27));
        fl_progress.addView(mProgressArc,params);

        mDownloadManager = DownloadManager.getInstance();
        mDownloadManager.registerObserver(this);
        fl_progress.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tv_des.setText(data.des);
        rb_star.setRating(data.stars);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tv_name.setText(data.name);
        mBitmapUtils.display(iv_icon, HttpHelper.URL + "image?name=" + data.iconUrl);

        DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(data);
        if (downloadInfo != null) {
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        } else {
            mCurrentState = DownloadManager.STATE_UNDO;
            mProgress = 0;
        }
        refreshUI(mCurrentState,mProgress,data.id);
    }

    private void refreshUI(int state, float progress, String id) {
        // 由于listview重用机制, 要确保刷新之前, 确实是同一个应用
        if (!getData().id.equals(id)) {
            return;
        }

        mCurrentState = state;
        mProgress = progress;
        switch (state) {
            case DownloadManager.STATE_UNDO:
                // 自定义进度条背景
                mProgressArc.setBackgroundResource(R.drawable.ic_download);
                // 没有进度
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("下载");
                break;
            case DownloadManager.STATE_WAITING:
                mProgressArc.setBackgroundResource(R.drawable.ic_download);
                // 等待模式
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tvDownload.setText("等待");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mProgressArc.setBackgroundResource(R.drawable.ic_pause);
                // 下载中模式
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                mProgressArc.setProgress(progress, true);
                tvDownload.setText((int) (progress * 100) + "%");
                break;
            case DownloadManager.STATE_PAUSE:
                mProgressArc.setBackgroundResource(R.drawable.ic_resume);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                break;
            case DownloadManager.STATE_ERROR:
                mProgressArc.setBackgroundResource(R.drawable.ic_redownload);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("下载失败");
                break;
            case DownloadManager.STATE_SUCCESS:
                mProgressArc.setBackgroundResource(R.drawable.ic_install);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText("安装");
                break;

            default:
                break;
        }
    }
    private void refreshUIOnMainThread(final DownloadInfo downloadInfo) {
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(downloadInfo.currentState,downloadInfo.getProgress(),downloadInfo.id);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }

    @Override
    public void onDownloadProgressChanged(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }
}
