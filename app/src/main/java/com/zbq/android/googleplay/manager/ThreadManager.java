package com.zbq.android.googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangbingquan on 2016/11/4.
 */

public class ThreadManager {
    private static ThreadPool sThreadPool;
    private static ThreadPoolExecutor sExecutor;

    public static ThreadPool getThreadPool() {
        if (sThreadPool == null) {
            synchronized (ThreadManager.class) {
                if (sThreadPool == null) {
                    //获取cpu数量
//                    int cpuCount = Runtime.getRuntime().availableProcessors();
//                    int threadCount = cpuCount * 2 + 1;
                    int threadCount =10;
                    sThreadPool = new ThreadPool(threadCount,threadCount,1L);
                }
            }
        }
        return sThreadPool;
    }


    public static class ThreadPool {
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable runnable) {
//            public ThreadPoolExecutor(int corePoolSize,
//            int maximumPoolSize,
//            long keepAliveTime,
//            TimeUnit unit,
//            BlockingQueue<Runnable> workQueue,
//            ThreadFactory threadFactory,
//            RejectedExecutionHandler handler)
            if (sExecutor == null) {
                sExecutor = new ThreadPoolExecutor(
                        corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy());
            }
            //执行一个Runnable对象
            sExecutor.execute(runnable);
        }
        public void cancel(Runnable runnable){
            //从线程队列张移除
            if (runnable!=null){
                sExecutor.getQueue().remove(runnable);
            }
        }
    }
}
