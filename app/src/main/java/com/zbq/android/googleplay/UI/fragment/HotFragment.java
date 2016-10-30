package com.zbq.android.googleplay.UI.fragment;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zbq.android.googleplay.UI.view.FlowLayout;
import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.http.protocol.HotProtocol;
import com.zbq.android.googleplay.utils.DrawableUtils;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class HotFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());

        int padding = UIUtils.dip2px(10);
        int padding7 = UIUtils.dip2px(7);

        flowLayout.setPadding(padding, padding, padding, padding);

        flowLayout.setHorizontalSpacing(UIUtils.dip2px(9));
        flowLayout.setVerticalSpacing(UIUtils.dip2px(8));

        for (int i = 0; i < mData.size(); i++) {
            TextView textView = new TextView(UIUtils.getContext());
            final String keyword=mData.get(i);
            textView.setText(mData.get(i));
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setPadding(padding, padding7, padding, padding7);
            textView.setGravity(Gravity.CENTER);
            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);
            int color = 0xffcecece;
//            GradientDrawable bgNormal =
//                    DrawableUtils.getGradientDrawable(Color.rgb(r, g, b), UIUtils.dip2px(6));
//            GradientDrawable bgPress=
//                    DrawableUtils.getGradientDrawable(0xffcecece, UIUtils.dip2px(6));
//            StateListDrawable selector = DrawableUtils.getSelector(bgNormal, bgPress);
            StateListDrawable selector = DrawableUtils.getSelector(Color.rgb(r, g, b), color, UIUtils.dip2px(10));
            textView.setBackgroundDrawable(selector);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), keyword, Toast.LENGTH_SHORT).show();
                }
            });
            flowLayout.addView(textView);
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol hotProtocol = new HotProtocol();
        mData = hotProtocol.getData(0);
        return check(mData);
    }
}
