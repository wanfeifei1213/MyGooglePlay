package com.zbq.android.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by zhangbingquan on 2016/10/30.
 */

public class DrawableUtils {
    public  static GradientDrawable getGradientDrawable(int color,int radius){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }
// 获取状态选择器
    public  static StateListDrawable getSelector(Drawable normal,Drawable press){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},press);//按下图片
        stateListDrawable.addState(new int[]{},normal);//默认图片
        return stateListDrawable;

    }

    public  static StateListDrawable getSelector(int normal,int press,int radius){
        GradientDrawable bgnormal = getGradientDrawable(normal, radius);
        GradientDrawable bgpress = getGradientDrawable(press, radius);
        StateListDrawable selector = getSelector(bgnormal, bgpress);

        return selector;

    }
}
