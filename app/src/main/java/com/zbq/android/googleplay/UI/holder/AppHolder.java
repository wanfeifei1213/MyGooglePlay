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
 * Created by zhangbingquan on 2016/10/28.
 */

public class AppHolder extends BaseHolder<AppInfo> {
    private BitmapUtils mBitmapUtils;
    private ImageView iv_icon;
    private TextView tv_name;
    private TextView tv_size;
    private RatingBar rb_star;
    private TextView tv_des;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_home);
        tv_des = (TextView) view.findViewById(R.id.tv_des);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        mBitmapUtils= BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tv_des.setText(data.des);
        rb_star.setRating(data.stars);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tv_name.setText(data.name);
        mBitmapUtils.display(iv_icon, HttpHelper.URL + "image?name=" + data.iconUrl);
    }
}
