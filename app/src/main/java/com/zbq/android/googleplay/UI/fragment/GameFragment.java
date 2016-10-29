package com.zbq.android.googleplay.UI.fragment;

import android.view.View;
import android.widget.TextView;

import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View onCreateSuccessView() {
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText("gameFragment");
        return textView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
