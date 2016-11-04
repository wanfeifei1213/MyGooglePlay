package com.zbq.android.googleplay.manager;

import android.content.Intent;
import android.net.Uri;

import com.zbq.android.googleplay.domain.AppInfo;
import com.zbq.android.googleplay.domain.DownloadInfo;
import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.IOUtils;
import com.zbq.android.googleplay.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangbingquan on 2016/11/4.
 */

public class DownloadManager {
    public static final int STATE_UNDO = 1;
    public static final int STATE_WAITING = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_DOWNLOADING = 4;
    public static final int STATE_ERROR = 5;
    public static final int STATE_SUCCESS = 6;

    private static DownloadManager sDownloadManager = new DownloadManager();
    //观察者集合
    private ArrayList<DownloadObserver> mObserverArrayList = new ArrayList<>();
    //    private HashMap<String, DownloadInfo> mDownloadInfoHashMap = new HashMap<>();
    private ConcurrentHashMap<String, DownloadInfo> mDownloadInfoHashMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, DownloadTask> mDownloadTaskHashMap = new ConcurrentHashMap<>();

    private void DownloadManager() {

    }

    public static DownloadManager getInstance() {
        return sDownloadManager;

    }

    //注册观察者
    public void registerObserver(DownloadObserver observer) {
        if (observer != null && !mObserverArrayList.contains(observer)) {
            mObserverArrayList.add(observer);
        }
    }

    //注销观察者
    public void unregisterObserver(DownloadObserver observer) {
        if (observer != null && mObserverArrayList.contains(observer)) {
            mObserverArrayList.remove(observer);
        }
    }

    //通知状态变化
    public void notifyDownloadStateChanged(DownloadInfo info) {
        for (DownloadObserver observer : mObserverArrayList) {
            observer.onDownloadStateChanged(info);
        }
    }

    //通知进度变化
    public void notifyDownloadProgressChanged(DownloadInfo info) {
        for (DownloadObserver observer : mObserverArrayList) {
            observer.onDownloadProgressChanged(info);
        }
    }

    public synchronized void download(AppInfo appInfo) {

        DownloadInfo downloadInfo = mDownloadInfoHashMap.get(appInfo.id);
        if (downloadInfo == null) {
            downloadInfo = DownloadInfo.copy(appInfo);
        }

        downloadInfo.currentState = STATE_WAITING;
        notifyDownloadStateChanged(downloadInfo);

        mDownloadInfoHashMap.put(downloadInfo.id, downloadInfo);

        DownloadTask task = new DownloadTask(downloadInfo);
        ThreadManager.getThreadPool().execute(task);
        mDownloadTaskHashMap.put(downloadInfo.id, task);

    }

    class DownloadTask implements Runnable {
        private DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            downloadInfo.currentState = STATE_DOWNLOADING;
            notifyDownloadStateChanged(downloadInfo);

            File file = new File(downloadInfo.path);

            HttpHelper.HttpResult httpResult;
            if (!file.exists()
                    || file.length() != downloadInfo.currentPos
                    || downloadInfo.currentPos == 0) {
                //从头下载
                file.delete();//删除无效文件
                downloadInfo.currentPos = 0;//
                httpResult = HttpHelper.download(
                        HttpHelper.URL + "download?name=" + downloadInfo.downloadUrl);
            } else {
                //断点续传
                httpResult = HttpHelper.download(
                        HttpHelper.URL + "download?name=" + downloadInfo.downloadUrl +
                                "&range=" + file.length());
            }
            if (httpResult != null && httpResult.getInputStream() != null) {
                InputStream in = httpResult.getInputStream();
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file, true);
                    int len = 0;
                    byte[] buffer = new byte[1024*4];

                    while ((len = in.read(buffer)) != -1 && downloadInfo.currentState == STATE_DOWNLOADING) {
                        out.write(buffer, 0, len);
                        out.flush();
                        //更新下载进度
                        downloadInfo.currentPos += len;
                        notifyDownloadProgressChanged(downloadInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(in);
                    IOUtils.close(out);
                }
                //文件下载结束
                if (file.length() == downloadInfo.size) {
                    downloadInfo.currentState = STATE_SUCCESS;
                    notifyDownloadStateChanged(downloadInfo);
                } else if (downloadInfo.currentState == STATE_PAUSE) {
                    notifyDownloadStateChanged(downloadInfo);
                } else {
                    file.delete();
                    downloadInfo.currentState = STATE_ERROR;
                    downloadInfo.currentPos = 0;
                    notifyDownloadStateChanged(downloadInfo);
                }
            } else {
                //网络异常
                file.delete();
                downloadInfo.currentState = STATE_ERROR;
                downloadInfo.currentPos = 0;
                notifyDownloadStateChanged(downloadInfo);
            }
            //从集合中移除下载任务
            mDownloadTaskHashMap.remove(downloadInfo.id);
        }
    }

    public synchronized void pause(AppInfo appInfo) {
        DownloadInfo downloadInfo = mDownloadInfoHashMap.get(appInfo.id);
        if (downloadInfo != null) {
            if (downloadInfo.currentState == STATE_DOWNLOADING || downloadInfo.currentState == STATE_WAITING) {
                downloadInfo.currentState = STATE_PAUSE;
                notifyDownloadStateChanged(downloadInfo);
                DownloadTask task = mDownloadTaskHashMap.get(downloadInfo.id);
                if (task != null) {
                    ThreadManager.getThreadPool().cancel(task);
                }
            }
        }
    }

    public synchronized void install(AppInfo appInfo) {
        DownloadInfo downloadInfo = mDownloadInfoHashMap.get(appInfo.id);
        if (downloadInfo != null) {
            // 跳到系统的安装页面进行安装
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + downloadInfo.path),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(intent);
        }
    }

    //说明观察者接口
    public interface DownloadObserver {

        public void onDownloadStateChanged(DownloadInfo info);

        public void onDownloadProgressChanged(DownloadInfo info);
    }

    public DownloadInfo getDownloadInfo(AppInfo appInfo) {
        return mDownloadInfoHashMap.get(appInfo.id);
    }
}
