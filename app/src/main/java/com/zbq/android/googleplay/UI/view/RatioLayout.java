package com.zbq.android.googleplay.UI.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.zbq.android.googleplay.R;

/**
 * Created by zhangbingquan on 2016/10/29.
 */

public class RatioLayout extends FrameLayout {

    private float mRatio;

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性值
//        attrs.getAttributeFloatValue()
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        typedArray.recycle();
        Log.d("typedArray", "RatioLayout: " + mRatio);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度
        //根据比例计算控件高度
        //重新测量控件

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && mRatio > 0) {

            int imageWidth = width - getPaddingLeft() - getPaddingRight();

            int imageHeight = (int) (imageWidth / mRatio + 0.5f);

            height = imageHeight + getPaddingTop() + getPaddingBottom();

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
