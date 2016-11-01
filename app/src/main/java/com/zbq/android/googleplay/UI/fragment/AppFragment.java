package com.zbq.android.googleplay.UI.fragment;

import android.view.View;

import com.zbq.android.googleplay.UI.adapter.MyBaseAdapter;
import com.zbq.android.googleplay.UI.holder.AppHolder;
import com.zbq.android.googleplay.UI.holder.BaseHolder;
import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.UI.view.MyListView;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.http.protocol.AppProtocol;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class AppFragment extends BaseFragment {

    private ArrayList<AppInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UIUtils.getContext());
        listView.setAdapter(new AppAdapter(data));
        return listView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        AppProtocol appProtocol = new AppProtocol();
        data = appProtocol.getData(0);

        return check(data);
    }
    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int postion) {
            return new AppHolder();
        }

        @Override
        public ArrayList onLoadMore() {
            AppProtocol appProtocol = new AppProtocol();
            ArrayList<AppInfo> moreData = appProtocol.getData(AppFragment.this.data.size());
            return moreData;
        }
    }
}
