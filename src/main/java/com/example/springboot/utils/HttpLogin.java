package com.example.springboot.utils;

import com.example.springboot.entity.Business;
import com.example.springboot.entity.Cookies;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HttpLogin {
    private static String url = "https://www.qichacha.com";

    //    static String cookess = "UM_distinctid=16dedc279ffda-00c31f5ee1336e-71415a3b-384000-16dedc27a007d7; zg_did=%7B%22did%22%3A%20%2216dedc279b95ea-0ddd5764adc299-71415a3b-384000-16dedc279ba2e4%22%7D; _uab_collina=157165200860208113024112; acw_tc=73dc081a15716520083808207ef1e701bb025f651402cccf08d06b5f07; QCCSESSID=e0fvq5t0a5fbqpe3sdojubalc3; hasShow=1; CNZZDATA1254842228=666878040-1571649716-https%253A%252F%252Fsp0.baidu.com%252F%7C1573816562; Hm_lvt_3456bee468c83cc63fb5147f119f1075=1573522745,1573795852,1573808604,1573816925; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201573820866253%2C%22updated%22%3A%201573821748306%2C%22info%22%3A%201573795851383%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22www.qichacha.com%22%2C%22zs%22%3A%200%2C%22sc%22%3A%200%2C%22cuid%22%3A%20%22a8fbd4c3b9268fc47ef96770cfab0758%22%7D; Hm_lpvt_3456bee468c83cc63fb5147f119f1075=1573821748";
    public static void main(String[] args) {
        //传入社会统一信用代码或者公司全称
//        search(URLEncoder.encode("高安红狮水泥有限公司"));

//        enter(url);
    }

    public static ResponseData<String> search(String key, Cookies cookie) {
        try {
            Thread.sleep(1000 * 30);
//            key = URLEncoder.encode(StringUtils.trim(key));
            HttpClient httpClient = HttpClientBuilder.create().build();
            System.out.println("模拟登录成功");
            // 进行登陆后的操作
            String dataUrl = "https://www.qichacha.com/search?key=" + key;
            HttpGet httpGet = new HttpGet(dataUrl);
            // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
            // 以下为本地登录后在浏览器内缓存的cookie值 *****每个人的都不一样
            httpGet.addHeader("cookie", cookie.getCookie());
            httpGet.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3956.0 Safari/537.36 Edg/80.0.328.4");
            HttpResponse execute = httpClient.execute(httpGet);
            // 返回值接收
            String text = EntityUtils.toString(execute.getEntity());
            Document doc = Jsoup.parse(text);
            // 页面属性选择 通过key值取到select的内容
            Elements links = doc.select("tbody>tr>td>a");
            // 选取第一个元素就是要访问的公司信息
            String href = links.get(0).attr("href");
            System.out.println(url + href);
            String enter = enter(url + href, cookie);
            if (StringUtils.isNotBlank(enter)) {
                return ResponseData.SUCCESS(enter);
            } else {
                return ResponseData.ERROR();
            }
        } catch (Exception e) {
            System.out.printf("***************************" + cookie.toString());
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

    public static String enter(String url, Cookies cookie) {
        //为了方便导出创建了javabean

        Business bus = new Business();

        try {
            Thread.sleep(1000 * 30);
            HttpClient httpClient = HttpClientBuilder.create().build();
            // 进行登陆后的操作
            HttpGet httpGet = new HttpGet(url);
            // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
            // 以下为本地登录后在浏览器内缓存的cookie值 *****每个人的都不一样
            httpGet.addHeader("cookie", cookie.getCookie());
            httpGet.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3956.0 Safari/537.36 Edg/80.0.328.4");
            HttpResponse execute = httpClient.execute(httpGet);
            // 返回值接收
            String text =EntityUtils.toString(execute.getEntity());
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
