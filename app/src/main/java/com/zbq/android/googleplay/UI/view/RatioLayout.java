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

public class RatioLayout extends FrameLayout  {

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
        Log.d("typedArray", "RatioLayout: "+mRatio);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
