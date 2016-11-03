package com.zbq.android.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by zhangbingquan on 2016/10/30.
 */

public class RecommendProtocol extends BaseProtocol<ArrayList<String>> {
    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja=new JSONArray(result);
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                String keyWord = ja.getString(i);
                arrayList.add(keyWord);
            }
            return  arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
