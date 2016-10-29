package com.zbq.android.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by zhangbingquan on 2016/10/27.
 */

public class GooglePlayApplication extends Application {

    private static Context sContext;
    private static Handler sHandler;
    private static int mainThreadId;

    public static Context getContext() {
        return sContext;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sHandler = new Handler();
        //主线程id
        mainThreadId = Process.myTid();
    }

}
