package com.zbq.android.googleplay.UI.activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.UI.fragment.BaseFragment;
import com.zbq.android.googleplay.UI.fragment.FragmentFactory;
import com.zbq.android.googleplay.UI.view.PagerTab;
import com.zbq.android.googleplay.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private PagerTab mPagertab;
    private ViewPager mViewpager;
    private MyAdapter mAdapter;
    private ActionBarDrawerToggle mToggle;

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
        initActionbar();
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

    public void initActionbar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);//返回左上角返回键
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(
                this,drawer, R.drawable.ic_drawer_am,R.string.drawer_open,R.string.drawer_close);

        mToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
