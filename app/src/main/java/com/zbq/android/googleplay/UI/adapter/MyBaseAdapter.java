package com.zbq.android.googleplay.UI.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.zbq.android.googleplay.UI.holder.BaseHolder;
import com.zbq.android.googleplay.UI.holder.MoreHolder;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 对adapter封装
 * Created by zhangbingquan on 2016/10/28.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private ArrayList<T> data;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_MORE = 1;


    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() + 1;//增加加载更多布局数量
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return TYPE_MORE;
        } else {
            return getInnerType();
        }
    }

    public int getInnerType() {
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == TYPE_MORE) {
                holder = new MoreHolder(hasMore());
            } else {
                holder = getHolder();
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        if (getItemViewType(position) != TYPE_MORE) {
            holder.setData(getItem(position));
        } else {

            MoreHolder moreHolder = (MoreHolder) holder;
            if (moreHolder.getData()==MoreHolder.STATE_MORE_MORE){
                loadMore(moreHolder);
            }
        }
        return holder.getRootView();
    }

    public boolean hasMore() {
        return true;
    }

    public abstract BaseHolder getHolder();

    private boolean isLoadMore = false;//标记是否正在加载更多

    //加载更多数据
    public void loadMore(final MoreHolder moreHolder) {
        if (!isLoadMore) {
            isLoadMore = true;
            new Thread() {
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                if (moreData.size() < 20) {
                                    moreHolder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                } else {
                                    moreHolder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                data.addAll(moreData);
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                moreHolder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            }.start();
        }
    }
    public int getListSize(){
        return data.size();
    }

    public abstract ArrayList<T> onLoadMore();
}
