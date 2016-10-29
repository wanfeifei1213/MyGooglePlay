package com.zbq.android.googleplay.http.protocol;


import com.zbq.android.googleplay.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/29.
 */

public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        try {
            ArrayList<SubjectInfo> arrayList = new ArrayList<>();
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                SubjectInfo info = new SubjectInfo();
                info.des = jo.getString("des");
                info.url = jo.getString("url");
                arrayList.add(info);
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
