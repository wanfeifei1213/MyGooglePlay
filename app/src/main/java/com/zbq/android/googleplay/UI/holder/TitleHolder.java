package com.zbq.android.googleplay.UI.holder;

import android.view.View;
import android.widget.TextView;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.CategoryInfo;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/10/31.
 */

public class TitleHolder extends BaseHolder <CategoryInfo>{

    private TextView mTv_title;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        mTv_title = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        mTv_title.setText(data.title);
    }
}
