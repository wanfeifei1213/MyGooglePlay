package com.zbq.android.googleplay.UI.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.BitmapHelper;
import com.zbq.android.googleplay.utils.UIUtils;

import java.util.ArrayList;

import static com.zbq.android.googleplay.R.id.rl_des_root;

/**
 * Created by zhangbingquan on 2016/11/2.
 */

public class DetailSafeHolder extends BaseHolder<AppInfo> {
    private ImageView[] mSafeIcons;// 安全标识图片
    private ImageView[] mDesIcons;// 安全描述图片
    private TextView[] mSafeDes;// 安全描述文字
    private LinearLayout[] mSafeDesBar;// 安全描述条目(图片+文字)
    private RelativeLayout rlDesRoot;
    private LinearLayout llDesRoot;
    private ImageView ivArrow;
    private BitmapUtils mBitmapUtils;
    private int mDesHeight;
    private LinearLayout.LayoutParams mLayoutParams;
    private boolean isOpen;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
        mSafeIcons = new ImageView[4];
        mSafeIcons[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        mSafeIcons[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        mSafeIcons[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        mSafeIcons[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        mDesIcons = new ImageView[4];
        mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
        mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
        mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
        mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

        mSafeDes = new TextView[4];
        mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

        mSafeDesBar = new LinearLayout[4];
        mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);
        rlDesRoot = (RelativeLayout) view.findViewById(rl_des_root);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);

        rlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        mBitmapUtils = BitmapHelper.getBitmapUtils();

        return view;
    }

    private void toggle() {
        ValueAnimator valueAnimator = null;
        if (isOpen) {
            isOpen = false;
            valueAnimator = ValueAnimator.ofInt(mDesHeight, 0);
        } else {
            isOpen = true;
            valueAnimator = ValueAnimator.ofInt(0, mDesHeight);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height = (Integer) animation.getAnimatedValue();
                mLayoutParams.height = height;
                llDesRoot.setLayoutParams(mLayoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isOpen) {
                    ivArrow.setImageResource(R.drawable.arrow_up);
                } else {
                    ivArrow.setImageResource(R.drawable.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });

        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<AppInfo.SafeInfo> safe = data.safe;

        for (int i = 0; i < 4; i++) {
            if (i < safe.size()) {
                // 安全标识图片
                AppInfo.SafeInfo safeInfo = safe.get(i);
                mBitmapUtils.display(mSafeIcons[i], HttpHelper.URL
                        + "image?name=" + safeInfo.safeUrl);
                // 安全描述文字
                mSafeDes[i].setText(safeInfo.safeDes);
                // 安全描述图片
                mBitmapUtils.display(mDesIcons[i], HttpHelper.URL
                        + "image?name=" + safeInfo.safeDesUrl);
            } else {
                // 剩下不应该显示的图片
                mSafeIcons[i].setVisibility(View.GONE);

                // 隐藏多余的描述条目
                mSafeDesBar[i].setVisibility(View.GONE);
            }
        }
        llDesRoot.measure(0, 0);
        mDesHeight = llDesRoot.getMeasuredHeight();
        mLayoutParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
        mLayoutParams.height = 0;
        llDesRoot.setLayoutParams(mLayoutParams);
    }
}
