package com.zbq.android.googleplay.UI.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.BitmapHelper;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/11/2.
 */

public class HomeDetailAppInfoHolder extends BaseHolder<AppInfo> {
    private ImageView iv_icon;
    private TextView tv_name;
    private RatingBar rb_star;
    private TextView tv_download_num;
    private TextView tv_version;
    private TextView tv_date;
    private TextView tv_size;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_appinfo);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_download_num = (TextView) view.findViewById(R.id.tv_download_num);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mBitmapUtils.display(iv_icon, HttpHelper.URL+"image?name="+data.iconUrl);
        tv_name.setText(data.name);
        tv_download_num.setText("下载量:"+data.downloadNum);
        tv_version.setText("版本号:"+data.version);
        tv_date.setText(data.date);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        rb_star.setRating(data.stars);
    }
}
