package com.translate;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.util.logging.Handler;

/**
 * 作者: guofeng
 * 日期: 16/8/25.
 */
public class HttpUtils {

    public static void httpGet(String search, ResponseCallBack responseCallBack) {
        if (TextUtils.isEmpty(search)) throw new NullPointerException("搜索内容不可为空");
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(search);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                if (responseCallBack != null) responseCallBack.onResponseComplete( JsonUtil.parserObject(result));
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (responseCallBack != null) responseCallBack.onResponseError(e.getMessage());
        } finally {
            try {
                if (response != null)
                    response.close();
                if (client != null)
                    client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface ResponseCallBack {

        void onResponseComplete(ExplainBean result);

        void onResponseError(String error);

    }

}
