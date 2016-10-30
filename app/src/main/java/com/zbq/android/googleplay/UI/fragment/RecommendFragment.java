package com.zbq.android.googleplay.UI.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.UI.view.fly.ShakeListener;
import com.zbq.android.googleplay.UI.view.fly.StellarMap;
import com.zbq.android.googleplay.http.protocol.RecommendProtocol;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class RecommendFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        stellarMap.setAdapter(new RecommendAdapter());
        stellarMap.setRegularity(6, 9);
        int padding = UIUtils.dip2px(10);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        //设置默认页面
        stellarMap.setGroup(0, true);

        ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellarMap.zoomIn();//跳到下一页
            }
        });
        return stellarMap;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        RecommendProtocol recommendProtocol = new RecommendProtocol();
        mData = recommendProtocol.getData(0);
        return check(mData);
    }

    class RecommendAdapter implements StellarMap.Adapter {

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            int count = mData.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                count += mData.size() % getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            position += group * getCount(group - 1);
            final String keyword = mData.get(position);
            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(keyword);
            Random random = new Random();

            int size = 16 + random.nextInt(10);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            int r = 30+random.nextInt(200);
            int g = 30+random.nextInt(200);
            int b = 30+random.nextInt(200);
            textView.setTextColor(Color.rgb(r,g,b));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),keyword, Toast.LENGTH_SHORT).show();
                }
            });
            return textView;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn) {
                if (group > 0) {
                    group--;
                } else {
                    group = getGroupCount() - 1;
                }
            } else {
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    group = 0;
                }
            }
            return group;
        }
    }
}
