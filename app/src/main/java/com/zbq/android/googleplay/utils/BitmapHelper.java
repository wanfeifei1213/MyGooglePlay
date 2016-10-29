package com.zbq.android.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by zhangbingquan on 2016/10/29.
 */

public class BitmapHelper {
    private static BitmapUtils sBitmapUtils = null;
    //单例 懒汉模式
    public static  BitmapUtils getBitmapUtils() {
        if (sBitmapUtils == null) {
            synchronized(BitmapHelper.class){
                if (sBitmapUtils==null){
                    sBitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return sBitmapUtils;
    }
}
