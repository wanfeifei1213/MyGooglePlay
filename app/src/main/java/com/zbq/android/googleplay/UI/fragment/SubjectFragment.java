package com.zbq.android.googleplay.UI.fragment;

import android.view.View;

import com.zbq.android.googleplay.UI.adapter.MyBaseAdapter;
import com.zbq.android.googleplay.UI.holder.BaseHolder;
import com.zbq.android.googleplay.UI.holder.SubjectHolder;
import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.UI.view.MyListView;
import com.zbq.android.googleplay.domain.SubjectInfo;
import com.zbq.android.googleplay.http.protocol.SubjectProtocol;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UIUtils.getContext());
        listView.setAdapter(new SubjectAdapter(data));
        return listView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        data = subjectProtocol.getData(0);
        return check(data);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol subjectProtocol = new SubjectProtocol();
            ArrayList<SubjectInfo> moreData = subjectProtocol.getData(SubjectFragment.this.data.size());
            return moreData;
        }
    }
}
