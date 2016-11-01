package com.zbq.android.googleplay.UI.fragment;

import android.view.View;

import com.zbq.android.googleplay.UI.adapter.MyBaseAdapter;
import com.zbq.android.googleplay.UI.holder.BaseHolder;
import com.zbq.android.googleplay.UI.holder.CategoryHolder;
import com.zbq.android.googleplay.UI.holder.TitleHolder;
import com.zbq.android.googleplay.UI.view.LoadingPage;
import com.zbq.android.googleplay.UI.view.MyListView;
import com.zbq.android.googleplay.domain.CategoryInfo;
import com.zbq.android.googleplay.http.protocol.CategoryProtocal;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        myListView.setAdapter(new CategoryAdapter(mData));
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocal categoryProtocal = new CategoryProtocal();
        mData = categoryProtocal.getData(0);
        return check(mData);
    }

    class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {

        public CategoryAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder(int position) {
            CategoryInfo info = mData.get(position);
            if (info.isTitle) {
                return new TitleHolder();
            } else {
                return new CategoryHolder();
            }
        }

        @Override
        public ArrayList onLoadMore() {
            return null;
        }

        @Override
        public boolean hasMore() {
            return false;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        @Override
        public int getInnerType(int position) {
            CategoryInfo info = mData.get(position);
            if (info.isTitle) {
                return super.getInnerType(position) + 1;
            } else {
                return super.getInnerType(position);
            }
        }
    }
}
