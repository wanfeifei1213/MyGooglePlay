package com.zbq.android.googleplay.UI.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_LOAD_UNDO = 1;//未加载
    private static final int STATE_LOAD_LOADING = 2;//正在加载
    private static final int STATE_LOAD_ERROR = 3;//加载失败
    private static final int STATE_LOAD_EMPTY = 4;//数据为空
    private static final int STATE_LOAD_SUCCESS = 5;//加载成功

    private int myCurrentState = STATE_LOAD_UNDO;
    private View mLoadingPage;
    private View mError_page;
    private View mEmpty_page;
    private View mSuccessPage;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);
        }
        if (mError_page == null) {
            mError_page = UIUtils.inflate(R.layout.page_error);
            Button btn_retry = (Button) mError_page.findViewById(R.id.btn_retry);
            btn_retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
            addView(mError_page);
        }
        if (mEmpty_page == null) {
            mEmpty_page = UIUtils.inflate(R.layout.page_empty);
            addView(mEmpty_page);
        }
        showRightPage();
    }

    private void showRightPage() {
//        if (myCurrentState==STATE_LOAD_UNDO||myCurrentState==STATE_LOAD_LOADING){
//            mLoadingPage.setVisibility(VISIBLE);
//        }else {
//            mLoadingPage.setVisibility(GONE);
//        }
        mLoadingPage
                .setVisibility((myCurrentState == STATE_LOAD_UNDO || myCurrentState == STATE_LOAD_LOADING) ?
                        VISIBLE : GONE);
        mError_page
                .setVisibility((myCurrentState == STATE_LOAD_ERROR) ?
                        VISIBLE : GONE);
        mEmpty_page
                .setVisibility((myCurrentState == STATE_LOAD_EMPTY) ?
                        VISIBLE : GONE);

        //当布局为空 并且当前状态为成功 才初始化布局
        if (mSuccessPage == null && myCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }
        if (mSuccessPage != null) {
            mSuccessPage
                    .setVisibility(myCurrentState == STATE_LOAD_SUCCESS ?
                            VISIBLE : GONE);
        }
    }

    public void loadData() {
        if (myCurrentState != STATE_LOAD_LOADING) {
            myCurrentState = STATE_LOAD_LOADING;
            new Thread() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                myCurrentState = resultState.getState();
                                showRightPage();
                            }
                        }
                    });
                }
            }.start();
        }
    }

    /**
     * 加载成功后显示的页面 必须由调用者实现
     *
     * @return
     */
    public abstract View onCreateSuccessView();

    /**
     * 加载网络数据
     */
    public abstract ResultState onLoad();

    public enum ResultState {
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR),
        STATE_SUCCESS(STATE_LOAD_SUCCESS);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
