package com.zbq.android.googleplay.UI.holder;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/11/4.
 */

public class DetailDownloadHolder extends BaseHolder<AppInfo> {
    private Button btn_fav;
    private Button btn_share;
    private Button btn_download;
    private FrameLayout fl_progress;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);
       fl_progress = (FrameLayout) view.findViewById(R.id.fl_progress);
       btn_download = (Button) view.findViewById(R.id.btn_download);
       btn_share = (Button) view.findViewById(R.id.btn_share);
       btn_fav = (Button) view.findViewById(R.id.btn_fav);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

    }
}
