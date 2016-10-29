package com.zbq.android.googleplay.UI.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.SubjectInfo;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.BitmapHelper;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/10/29.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo> {
    private android.widget.ImageView iv_pic;
    private android.widget.TextView tv_title;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        tv_title.setText(data.des);
        mBitmapUtils.display(iv_pic, HttpHelper.URL + "image?name=" + data.url);
    }
}
