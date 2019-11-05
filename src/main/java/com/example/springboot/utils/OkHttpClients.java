package com.example.springboot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: http访问工具类
 * @Description:
 * @author: tongzhijie
 * @date: 2019/10/28
 */
public class OkHttpClients {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";
    public final static String PATCH = "PATCH";
    private final static String UTF8 = "UTF-8";

    private final static OkHttpClient client = new OkHttpClient.Builder().
            connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES)).
            readTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS).build();

    public static OkhttpResult getSyn(String url) {

        return OkHttpClients.getSyn(url, null, null);
    }

    public static OkhttpResult getSyn(String url, String paramStr) {


        return null;
    }

    public static OkhttpResult getSyn(String url, Map<String, Object> param) {
        OkhttpResult syn = OkHttpClients.getSyn(url, param, null);

        return syn;
    }

    public static OkhttpResult getSyn(String url, Map<String, Object> params, Map<String, Object> headerParam) {
        // 创建请求
        if (url == null) {
            return new OkhttpResult(HttpStatus.SC_INTERNAL_SERVER_ERROR, "网址不能为空！", null);
        }
        try {
            if (params != null) {
                url += OkHttpClients.getParams(params);
            }
            Headers headers = null;
            if (headerParam != null) {
                headers = setHeaders(headerParam);
            }
            Request request = null;
            if(headers!= null){
                request =  new Request.Builder().url(url).headers(headers).get().build();
            }else{
                request =  new Request.Builder().url(url).get().build();
            }


            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseString = responseBody.string();
                    return new OkhttpResult<>(response.code(), response.message(), responseString);
                } else {
                    return new OkhttpResult<>(response.code(), response.message(), null);
                }
            } else {
                return new OkhttpResult(response.code(), response.message(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new OkhttpResult(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }


    public static OkhttpResult getSyn(String url, JSONObject params, JSONObject headers) {


        return null;
    }

    public static <T> OkhttpResult<T> getSyn(String url, JSONObject params, JSONObject headers, Class<T> tClass) {


        return null;
    }

    public static OkhttpResult getAsyn(String url) {


        return null;
    }

    public static OkhttpResult getAsyn(String url, String paramStr) {


        return null;
    }

    public static OkhttpResult getAsyn(String url, Map<String, Object> param) {
        OkhttpResult syn = OkHttpClients.getSyn(url, param, null);

        return syn;
    }

    public static OkhttpResult getAsyn(String url, Map<String, Object> params, Map<String, Object> headers) {

        return null;
    }

    public static OkhttpResult getAsyn(String url, JSONObject params, JSONObject headers) {

        return null;
    }

    public static <T> OkhttpResult<T> getAsyn(String url, JSONObject params, JSONObject headers, Class<T> tClass) {

        return null;
    }

    public static OkhttpResult postSyn(String url) {

        return null;
    }

    public static OkhttpResult postSyn(String url, String paramStr) {

        return null;
    }

    public static OkhttpResult postSyn(String url, Map<String, Object> param) {
        return null;
    }

    public static OkhttpResult postSyn(String url, Map<String, Object> params, Map<String, Object> headers) {
        return null;
    }

    public static OkhttpResult postSyn(String url, JSONObject params, JSONObject headers) {
        return null;
    }

    public static <T> OkhttpResult<T> postSyn(String url, JSONObject params, JSONObject headers, Class<T> tClass) {
        return null;
    }

    public static OkhttpResult postAsyn(String url) {
        return null;
    }

    public static OkhttpResult postAsyn(String url, String paramStr) {
        return null;
    }

    public static OkhttpResult postAsyn(String url, Map<String, Object> param) {
        return null;
    }

    public static OkhttpResult postAsyn(String url, Map<String, Object> params, Map<String, Object> headers) {
        return null;
    }

    public static OkhttpResult postAsyn(String url, JSONObject params, JSONObject headers) {
        return null;
    }

    public static <T> OkhttpResult<T> postAsyn(String url, JSONObject params, JSONObject headers, Class<T> tClass) {
        return null;
    }


    public static OkhttpResult getSynProxy(String url) {
        return null;
    }

    public static OkhttpResult getSynProxy(String url, String paramStr) {
        return null;
    }



    public static String getParams(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer("?");
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> item : params.entrySet()) {
                Object value = item.getValue();
                if (value != null) {
                    sb.append("&");
                    sb.append(item.getKey());
                    sb.append("=");
                    sb.append(value);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    public static Headers setHeaders(Map<String, Object> headersParam) {
        Headers headers = null;
        okhttp3.Headers.Builder headersbuilder = new okhttp3.Headers.Builder();
        if (headersParam != null && !headersParam.isEmpty()) {
            Iterator<String> iterator = headersParam.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headers.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }


}
