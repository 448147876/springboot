package com.example.springboot.utils;


import lombok.Data;
import lombok.ToString;
import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.*;

/**
 * 代理池
 */
@Service
public class ProxyPoolService {


    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String PROXY_URL = "https://www.xicidaili.com/nn/";

    private static final String PREFX_PROXY = "webcrawler.proxy.list";


    /**
     * 获取可用代理
     *
     * @param url
     * @return
     */
    public HttpHost getProxy(String url) {
        Object index = redisTemplate.opsForList().index(PREFX_PROXY, 0);
        try {
            IPBean ipBean = (IPBean) index;
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBean.getUrl(), ipBean.getPort()));
            URLConnection httpCon = new URL(url).openConnection(proxy);
            httpCon.setConnectTimeout(5000);
            httpCon.setReadTimeout(5000);
            int code = ((HttpURLConnection) httpCon).getResponseCode();
            if (code == 200) {
                return new HttpHost(ipBean.getUrl(), ipBean.getPort());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        getHttpsPorts();
        for (int i = 0; i < redisTemplate.opsForList().size(PREFX_PROXY); i++) {
            try {
                IPBean ipBean = (IPBean) redisTemplate.opsForList().index(PREFX_PROXY, 0);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipBean.getUrl(), ipBean.getPort()));
                URLConnection httpCon = new URL(url).openConnection(proxy);
                httpCon.setConnectTimeout(5000);
                httpCon.setReadTimeout(5000);
                int code = ((HttpURLConnection) httpCon).getResponseCode();
                if (code == 200) {
                    return new HttpHost(ipBean.getUrl(), ipBean.getPort());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            redisTemplate.opsForList().leftPop(PREFX_PROXY);

        }

        return null;
    }

    /**
     * 到西刺代理 上抓取代理信息
     */
    private void getHttpsPorts() {
        if (redisTemplate.opsForList().size(PREFX_PROXY) <= 100) {
//            List<IPBean> beanList = new ArrayList<>();
            //重新获取
            for (int i = 0; i < 100; i++) {
                String index = String.valueOf(i + 1);
                if (i == 0) {
                    index = null;
                }
                String html = HttpUtils.getRequest(PROXY_URL + index);

                Document document = Jsoup.parse(html);
                Elements eles = document.selectFirst("table").select("tr");

                for (int j = 0; j < eles.size(); j++) {
                    if (j == 0) {
                        continue;
                    }
                    Element ele = eles.get(j);
                    String ip = ele.children().get(1).text();
                    int port = Integer.parseInt(ele.children().get(2).text().trim());
                    String typeStr = ele.children().get(5).text().trim();

                    IPBean ipBean = new IPBean(ip, port, typeStr);
                    redisTemplate.opsForList().leftPush(PREFX_PROXY, ipBean);
                }
            }
        }


    }


}


@Data
@ToString
class IPBean {
    private String url;
    private Integer port;
    private String type;

    public IPBean(String url, Integer port, String type) {
        this.url = url;
        this.port = port;
        this.type = type;
    }
}
