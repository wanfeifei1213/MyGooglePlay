package com.zbq.android.googleplay.UI.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zbq.android.googleplay.R;
import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.utils.UIUtils;

/**
 * Created by zhangbingquan on 2016/11/3.
 */

public class DetailDesHolder extends BaseHolder<AppInfo> {
    private TextView tvdetaildes;
    private TextView tvdetailauthor;
    private ImageView ivarrow;
    private RelativeLayout rldetailtoggle;
    private LinearLayout.LayoutParams mLayoutParams;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);
        rldetailtoggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);
        ivarrow = (ImageView) view.findViewById(R.id.iv_arrow);
        tvdetailauthor = (TextView) view.findViewById(R.id.tv_detail_author);
        tvdetaildes = (TextView) view.findViewById(R.id.tv_detail_des);
        rldetailtoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return view;
    }

    private boolean isOpen = false;


    @Override
    public void refreshView(AppInfo data) {
        tvdetaildes.setText(data.des);
        tvdetailauthor.setText(data.author);

        tvdetaildes.post(new Runnable() {
            @Override
            public void run() {
                int shortHeight = getShortHeight();
                mLayoutParams = (LinearLayout.LayoutParams) tvdetaildes.getLayoutParams();
                mLayoutParams.height = shortHeight;
                tvdetaildes.setLayoutParams(mLayoutParams);
            }
        });
    }

    private int getShortHeight() {
        int width = tvdetaildes.getMeasuredWidth();

        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getData().des);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setMaxLines(7);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);

        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();

    }

    private int getLongHeight() {
        int width = tvdetaildes.getMeasuredWidth();

        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getData().des);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        textView.setMaxLines(7);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);

        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();

    }

    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        ValueAnimator valueAnimator = null;

        if (isOpen) {
            isOpen = false;
            if (longHeight > shortHeight) {
                valueAnimator = ValueAnimator.ofInt(longHeight, shortHeight);
            }
        } else {
            isOpen = true;
            if (longHeight > shortHeight) {
                valueAnimator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }
        if (valueAnimator != null) {
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer height = (Integer) animation.getAnimatedValue();
                    mLayoutParams.height = height;
                    tvdetaildes.setLayoutParams(mLayoutParams);
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    final ScrollView scrollView = getScrollView();
//                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    if (isOpen) {
                        ivarrow.setImageResource(R.drawable.arrow_up);
                    } else {
                        ivarrow.setImageResource(R.drawable.arrow_down);
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
    }

    private ScrollView getScrollView() {
        ViewParent parent = tvdetaildes.getParent();
        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }
        return (ScrollView) parent;
    }
}
