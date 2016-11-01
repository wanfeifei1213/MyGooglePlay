package com.zbq.android.googleplay.UI.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.CategoryInfo;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.BitmapHelper;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/10/31.
 */

public class CategoryHolder extends BaseHolder<CategoryInfo> implements View.OnClickListener {

    private android.widget.ImageView iv_icon3, iv_icon2, iv_icon1;
    private android.widget.TextView tv_name3, tv_name2, tv_name1;
    private android.widget.LinearLayout ll_grid3, ll_grid2, ll_grid1;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_category);
        ll_grid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);
        tv_name3 = (TextView) view.findViewById(R.id.tv_name3);
        iv_icon3 = (ImageView) view.findViewById(R.id.iv_icon3);
        ll_grid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        tv_name2 = (TextView) view.findViewById(R.id.tv_name2);
        iv_icon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        ll_grid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        tv_name1 = (TextView) view.findViewById(R.id.tv_name1);
        iv_icon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        ll_grid1.setOnClickListener(this);
        ll_grid2.setOnClickListener(this);
        ll_grid3.setOnClickListener(this);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tv_name3.setText(data.name3);
        tv_name2.setText(data.name2);
        tv_name1.setText(data.name1);
        mBitmapUtils.display(iv_icon1, HttpHelper.URL + "image?name=" + data.url1);
        mBitmapUtils.display(iv_icon2, HttpHelper.URL + "image?name=" + data.url2);
        mBitmapUtils.display(iv_icon3, HttpHelper.URL + "image?name=" + data.url3);
    }

    @Override
    public void onClick(View v) {
        CategoryInfo info = getData();

        switch (v.getId()) {
            case R.id.ll_grid1:
                Toast.makeText(UIUtils.getContext(), info.name1, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid2:
                Toast.makeText(UIUtils.getContext(), info.name2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid3:
                Toast.makeText(UIUtils.getContext(), info.name3, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
