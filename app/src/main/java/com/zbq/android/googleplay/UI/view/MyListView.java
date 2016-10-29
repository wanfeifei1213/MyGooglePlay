package com.zbq.android.googleplay.UI.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zhangbingquan on 2016/10/29.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }



    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setSelector(new ColorDrawable());//设置listview状态选择器
        this.setDivider(null);//设置分割线listview divider  null为全透明
        this.setCacheColorHint(Color.TRANSPARENT);//有时滑动listview 背景会变成黑色 此方法设置背景为全透明
    }

}
