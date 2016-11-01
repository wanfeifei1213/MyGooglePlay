package com.zbq.android.googleplay.UI.fragment;

import android.view.View;

import com.zbq.android.googleplay.UI.adapter.MyBaseAdapter;
import com.zbq.android.googleplay.UI.holder.BaseHolder;
import com.zbq.android.googleplay.UI.holder.HomeHeaderHolder;
import com.zbq.android.googleplay.UI.holder.HomeHolder;
import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.UI.view.MyListView;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.http.protocol.HomeProtocol;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class HomeFragment extends BaseFragment {
    private ArrayList<AppInfo> data;
    private ArrayList<String> mPictureList;

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UIUtils.getContext());
        HomeHeaderHolder homeHeaderHolder = new HomeHeaderHolder();
        listView.addHeaderView(homeHeaderHolder.getRootView());
        if (mPictureList != null) {
            homeHeaderHolder.setData(mPictureList);
        }
        listView.setAdapter(new HomeAdapter(data));
        return listView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HomeProtocol homeProtocol = new HomeProtocol();
        data = homeProtocol.getData(0);
        mPictureList = homeProtocol.getPictureList();
        return check(data);
    }


    class HomeAdapter extends MyBaseAdapter<AppInfo> {

        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new HomeHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
//            ArrayList<String> moreData = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                moreData.add("更多数据"+i);
//            }
//            SystemClock.sleep(2000);
            HomeProtocol homeProtocol = new HomeProtocol();
            ArrayList<AppInfo> moreData = homeProtocol.getData(getListSize());
            return moreData;
        }

        @Override
        public boolean hasMore() {
            return true;
        }
    }
}
