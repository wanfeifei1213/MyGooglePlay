package com.zbq.android.googleplay.UI.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/10/28.
 */

public class MoreHolder extends BaseHolder<Integer> {

    public static final int STATE_MORE_MORE = 1;
    public static final int STATE_MORE_ERROR = 2;
    public static final int STATE_MORE_NONE = 3;
    private android.widget.LinearLayout ll_load_more;
    private android.widget.TextView tv_load_error;

    public MoreHolder(boolean hasMore) {
//        if (hasMore) {
//            setData(STATE_MORE_ERROR);
//        } else {
//            setData(STATE_MORE_NONE);
//        }
        setData(hasMore ? STATE_MORE_MORE : STATE_MORE_NONE);
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        tv_load_error = (TextView) view.findViewById(R.id.tv_load_error);
        ll_load_more = (LinearLayout) view.findViewById(R.id.ll_load_more);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data) {
            case STATE_MORE_MORE:
                ll_load_more.setVisibility(View.VISIBLE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
