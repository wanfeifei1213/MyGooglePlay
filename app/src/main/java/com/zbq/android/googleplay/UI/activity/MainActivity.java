package com.zbq.android.googleplay.UI.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.UI.fragment.BaseFragment;
import com.zbq.android.googleplay.UI.fragment.FragmentFactory;
import com.zbq.android.googleplay.UI.view.PagerTab;
import com.zbq.android.googleplay.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private PagerTab mPagertab;
    private ViewPager mViewpager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mPagertab = (PagerTab) findViewById(R.id.pagertab);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);
        mPagertab.setViewPager(mViewpager);
        mPagertab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                //加载数据
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {

        private final String[] mTabName;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabName = UIUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabName[position];
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTabName.length;
        }
    }
}
