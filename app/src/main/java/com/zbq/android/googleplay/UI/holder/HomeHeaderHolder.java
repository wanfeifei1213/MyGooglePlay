package com.zbq.android.googleplay.UI.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;
import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.BitmapHelper;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/11/1.
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

    private ViewPager mViewPager;
    private ArrayList<String> data;
    private BitmapUtils mBitmapUtils;
    private LinearLayout mLinearLayout;
    private int mPrePoint;

    @Override
    public View initView() {
        RelativeLayout relativeLayout = new RelativeLayout(UIUtils.getContext());
        AbsListView.LayoutParams reParams = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
        relativeLayout.setLayoutParams(reParams);

        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(vpParams);

        relativeLayout.addView(mViewPager);

        mLinearLayout = new LinearLayout(UIUtils.getContext());
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padding = UIUtils.dip2px(10);
        mLinearLayout.setPadding(padding, padding, padding, padding);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mLinearLayout.setLayoutParams(llParams);

        relativeLayout.addView(mLinearLayout);

        return relativeLayout;
    }

    @Override
    public void refreshView(ArrayList<String> data) {
        this.data = data;
        mViewPager.setAdapter(new HomeHeadAdapter());
        mViewPager.setCurrentItem(data.size() * 10000);

        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                point.setImageResource(R.drawable.indicator_selected);
            } else {
                point.setImageResource(R.drawable.indicator_normal);
                layoutParams.leftMargin = UIUtils.dip2px(4);
            }
            point.setLayoutParams(layoutParams);
            mLinearLayout.addView(point);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % HomeHeaderHolder.this.data.size();
                ImageView point = (ImageView) mLinearLayout.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                ImageView perPoint = (ImageView) mLinearLayout.getChildAt(mPrePoint);
                perPoint.setImageResource(R.drawable.indicator_normal);
                mPrePoint = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        HomeHeaderTask homeHeaderTask = new HomeHeaderTask();
        homeHeaderTask.start();
    }

    class HomeHeaderTask implements Runnable {
        public void start() {
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this, 3000);
        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
            UIUtils.getHandler().postDelayed(this, 3000);
        }
    }

    class HomeHeadAdapter extends PagerAdapter {

        public HomeHeadAdapter() {
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }

        @Override
        public int getCount() {
//            return data.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % data.size();
            String url = data.get(position);
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(imageView, HttpHelper.URL + "image?name=" + url);

            container.addView(imageView);
            return imageView;
        }
    }


}
