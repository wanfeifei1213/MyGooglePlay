package com.zbq.android.googleplay.http.protocol;

import android.util.Log;

import com.zbq.android.googleplay.http.HttpHelper;
import com.zbq.android.googleplay.utils.IOUtils;
import com.zbq.android.googleplay.utils.StringUtils;
import com.zbq.android.googleplay.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhangbingquan on 2016/10/28.
 */

public abstract class BaseProtocol<T> {
    public T getData(int index) {
        String result = getCache(index);
        if (StringUtils.isEmpty(result)) {
            result = getDataFromServer(index);

        }
        //解析数据
        if (!StringUtils.isEmpty(result)) {
            T data = parseData(result);
            return data;
        }
        return null;
    }

    private String getDataFromServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParams());
        if (httpResult != null) {
            String result = httpResult.getString();

            if (!StringUtils.isEmpty(result)){//写缓存
                setCache(index,result);
            }
            Log.d("getDataFromServer", "onLoad: "+result);
            return result;
        }
        return null;
    }

    public abstract String getKey();

    public abstract String getParams();

    //写缓存
    //以UIL为Key 以json为value
    public void setCache(int index, String json) {
        //以Url为文件名 以json为内容
        File cacheDir = UIUtils.getContext().getCacheDir();//应用的缓存文件夹
        //生成缓存文件
        File cachaFile = new File(cacheDir, getKey() + "?index=" + index + getParams());
        FileWriter writer = null;
        try {
            writer = new FileWriter(cachaFile);
            long deadline = System.currentTimeMillis() + 30 * 60 * 1000;//本个小时有效期
            writer.write(deadline + "\n");//第一行写入缓存时间
            writer.write(json);//写入js on
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    public String getCache(int index) {
        File cacheDir = UIUtils.getContext().getCacheDir();//应用的缓存文件夹
        File cachaFile = new File(cacheDir, getKey() + "?index=" + index + getParams());

        //判断缓存是否存在
        if (cachaFile.exists()) {
            //判断缓存是否有效
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cachaFile));
                String deadline = reader.readLine();
                long deadtime = Long.parseLong(deadline);
                if (System.currentTimeMillis() < deadtime) {//当前事件小于有效时间
                    //缓存有效
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    public abstract T parseData(String result);
}
