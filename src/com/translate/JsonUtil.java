package com.translate;


import com.google.gson.Gson;

/**
 * 作者: guofeng
 * 日期: 16/8/25.
 */
public class JsonUtil {

    public static ExplainBean parserObject(String result) {
        Gson gson = new Gson();
        return gson.fromJson(result, ExplainBean.class);
    }

}
