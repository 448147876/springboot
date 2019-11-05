package com.example.springboot.service;


import com.alibaba.fastjson.JSONObject;
import com.example.springboot.entity.IPBeanDTO;
import com.example.springboot.utils.HttpClientUtil;
import com.example.springboot.utils.JsonUtils;
import com.example.springboot.utils.OkHttpClients;
import com.example.springboot.utils.OkhttpResult;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.*;

/**
 * 代理池,如果代理少于100条，则动态从西刺代理上获取代理ip
 */
@Service
public class ProxyPoolService {


    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private static final String PROXY_URL = "https://www.xicidaili.com/nn/";

    private static final String PREFX_PROXY = "webcrawler.proxy.list";


    /**
     * 获取可用代理
     *
     * @param url
     * @return
     */
    public Proxy getProxy(String url) throws Exception {
        String context = redisTemplate.opsForList().index(PREFX_PROXY, 0);
        try {
            IPBeanDTO ipBeanDTO = null;
            if(StringUtils.isNotEmpty(context)){
                ipBeanDTO = JSONObject.parseObject(context, IPBeanDTO.class);
            }

            if(ipBeanDTO == null){
                getHttpsPorts();
                context = redisTemplate.opsForList().index(PREFX_PROXY, 0);
                ipBeanDTO = JSONObject.parseObject(context, IPBeanDTO.class);
            }
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBeanDTO.getUrl(), ipBeanDTO.getPort()));
            URLConnection httpCon = new URL(url).openConnection(proxy);
            httpCon.setConnectTimeout(5000);
            httpCon.setReadTimeout(5000);
            int code = ((HttpURLConnection) httpCon).getResponseCode();
            if (code == 200) {
                return  proxy;
            }

        } catch (Exception e) {

        }


        getHttpsPorts();
        for (int i = 0; i < redisTemplate.opsForList().size(PREFX_PROXY); i++) {
            try {
                IPBeanDTO ipBeanDTO = null;
                context = redisTemplate.opsForList().index(PREFX_PROXY, 0);
                if(StringUtils.isNotEmpty(context)){
                    ipBeanDTO = JSONObject.parseObject(context, IPBeanDTO.class);
                }

                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBeanDTO.getUrl(), ipBeanDTO.getPort()));
                URLConnection httpCon = new URL(url).openConnection(proxy);
                httpCon.setConnectTimeout(5000);
                httpCon.setReadTimeout(5000);
                int code = ((HttpURLConnection) httpCon).getResponseCode();
                if (code == 200) {
                    return proxy;
                }

            } catch (Exception e) {

            }

            redisTemplate.opsForList().leftPop(PREFX_PROXY);

        }

        return null;
    }

    /**
     * 到西刺代理 上抓取代理信息
     */
    private void getHttpsPorts() throws Exception {
        if (redisTemplate.opsForList().size(PREFX_PROXY) <= 100) {
            //重新获取
            for (int i = 0; i < 5; i++) {
                String index = String.valueOf(i + 1);
                if (i == 0) {
                    index = "";
                }
                Thread.sleep(1000*5);
                String result = HttpClientUtil.doGet(PROXY_URL + index,null);

                Document document = Jsoup.parse(result);
                Elements eles = document.selectFirst("table").select("tr");

                for (int j = 0; j < eles.size(); j++) {
                    if (j == 0) {
                        continue;
                    }
                    Element ele = eles.get(j);
                    String ip = ele.children().get(1).text();
                    int port = Integer.parseInt(ele.children().get(2).text().trim());
                    String typeStr = ele.children().get(5).text().trim();

                    IPBeanDTO ipBeanDTO = new IPBeanDTO(ip, port, typeStr);
                    redisTemplate.opsForList().leftPush(PREFX_PROXY, JSONObject.toJSONString(ipBeanDTO));
                }
            }
        }


    }


}


