package com.zbq.android.googleplay.domain;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhangbingquan on 2016/11/4.
 */

public class DownloadInfo {

    public String id;
    public String name;
    public String downloadUrl;
    public String packageName;
    public long size;

    public long currentPos;
    public int currentState;

    public static final String GOOGLE_MARKET = "GOOGLE_MARKET";
    public static final String DOWNLOAD = "download";
    public String path;

    //获取下载进度（0-1）
    public float getProgress() {
        if (size == 0) {
            return 0f;
        }
        return currentPos / (float) size;
    }

    public String getFilePath() {
        StringBuffer sb = new StringBuffer();
        String sdCard = Environment.getExternalStorageDirectory().
                getAbsolutePath();
        sb.append(sdCard);
        sb.append(File.separator);
        sb.append(GOOGLE_MARKET);
        sb.append(File.separator);
        sb.append(DOWNLOAD);
        if (createDir(sb.toString())) {
            return sb.toString() + File.separator + name + ".akp";
        }
        return null;
    }

    private boolean createDir(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return dirFile.mkdirs();
        }
        return true;
    }

    public static DownloadInfo copy(AppInfo appInfo) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = appInfo.id;
        downloadInfo.name = appInfo.name;
        downloadInfo.downloadUrl = appInfo.downloadUrl;
        downloadInfo.packageName = appInfo.packageName;
        downloadInfo.size = appInfo.size;
        downloadInfo.currentPos = 0;
        downloadInfo.currentState = com.zbq.android.googleplay.manager.DownloadManager.STATE_UNDO;
        downloadInfo.path = downloadInfo.getFilePath();
        return downloadInfo;
    }
}
