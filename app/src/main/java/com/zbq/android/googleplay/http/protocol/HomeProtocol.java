package com.zbq.android.googleplay.http.protocol;

import com.zbq.android.googleplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/28.
 */

public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {

    private ArrayList<String> mPictures;

    @Override
    public String getKey() {

        return "home";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
//使用JSONObject解析 遇到{ } 就是JSONObject ；遇到[]就是JsonArray
        try {
            JSONObject jo = new JSONObject(result);
            JSONArray ja = jo.getJSONArray("list");
            ArrayList<AppInfo> appInfoArrayList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo info = new AppInfo();
                info.des = jo1.getString("des");
                info.downloadUrl = jo1.getString("downloadUrl");
                info.iconUrl = jo1.getString("iconUrl");
                info.id = jo1.getString("id");
                info.name = jo1.getString("name");
                info.packageName = jo1.getString("packageName");
                info.size = jo1.getLong("size");
                info.stars = (float) jo1.getDouble("stars");
                appInfoArrayList.add(info);

            }

            JSONArray ja1 = jo.getJSONArray("picture");
            mPictures = new ArrayList<>();
            for (int i = 0; i < ja1.length(); i++) {
                String pic = ja1.getString(i);
                mPictures.add(pic);
            }
            return appInfoArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getPictureList(){
        return mPictures;
    }
}
