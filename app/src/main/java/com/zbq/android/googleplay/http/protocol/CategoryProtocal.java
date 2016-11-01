package com.zbq.android.googleplay.http.protocol;

import com.zbq.android.googleplay.domain.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/31.
 */

public class CategoryProtocal extends BaseProtocol<ArrayList<CategoryInfo>> {
    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategoryInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<CategoryInfo> arrayList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo.has("title")) {
                    CategoryInfo titleInfo = new CategoryInfo();
                    titleInfo.title = jo.getString("title");
                    titleInfo.isTitle = true;
                    arrayList.add(titleInfo);
                }
                if (jo.has("infos")) {
                    JSONArray ja1 = jo.getJSONArray("infos");
                    for (int j = 0; j < ja1.length(); j++) {
                        JSONObject jo1 = ja1.getJSONObject(j);
                        CategoryInfo info = new CategoryInfo();
                        info.name1 = jo1.getString("name1");
                        info.name2 = jo1.getString("name2");
                        info.name3 = jo1.getString("name3");
                        info.url1 = jo1.getString("url1");
                        info.url2 = jo1.getString("url2");
                        info.url3 = jo1.getString("url3");
                        info.isTitle = false;
                        arrayList.add(info);

                    }
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
