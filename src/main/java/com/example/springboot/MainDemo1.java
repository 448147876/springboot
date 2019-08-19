package com.example.springboot;
import	java.util.Map;

import com.example.springboot.utils.G_Code;
import com.example.springboot.utils.HttpUtils;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

public class MainDemo1 {
    @Test
    public void demo1() throws IOException {
        Document doc = Jsoup.connect("https://www.tianyancha.com/company/955991535").get();
        System.out.println(doc.title());
    }

    @Test
    public void demo2(){
        long time = System.currentTimeMillis()+10000;
        System.out.println(time);
//        String key = "12100000400000456";
//        String url = "https://sp0.tianyancha.com/search/suggestV2.json?key="+key+"&_="+time;
//        String request = HttpUtils.getRequest(url);
//        System.out.println(request);
    }

    @Test
    public void demo3() throws IOException, InterruptedException {
        int i = 0;
        String charStr = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        char[] chars = charStr.toCharArray();
        long index = 91350582310612087L;
        Map<String, String> params = Maps.newHashMap();
        params.put("Cookie", "QCCSESSID=e2cr8uhrpcbf3r70kc23hia5p4; zg_did=%7B%22did%22%3A%20%2216c83dd79c068e-030ca5931dcb03-7373e61-1fa400-16c83dd79c1bdf%22%7D; UM_distinctid=16c83dd7a39a10-078262d8baa9aa-7373e61-1fa400-16c83dd7a3a42c; _uab_collina=156558042587509283636563; acw_tc=73dc081e15655804230666614ec8c0a7f88dde7e505ad744a033ade8d0; hasShow=1; CNZZDATA1254842228=860200595-1565575546-https%253A%252F%252Fwww.baidu.com%252F%7C1565921176; Hm_lvt_3456bee468c83cc63fb5147f119f1075=1565679509,1565762908,1565916361,1565923166; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201565923166195%2C%22updated%22%3A%201565923257800%2C%22info%22%3A%201565580425672%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%5C%22%24utm_source%5C%22%3A%20%5C%22baidu%5C%22%2C%5C%22%24utm_medium%5C%22%3A%20%5C%22cpc%5C%22%2C%5C%22%24utm_term%5C%22%3A%20%5C%22%E4%BC%81%E4%B8%9A%E6%9F%A5%E8%AF%A2%E5%A4%9A%E8%AF%8D1%5C%22%7D%22%2C%22referrerDomain%22%3A%20%22www.baidu.com%22%2C%22cuid%22%3A%20%22a8fbd4c3b9268fc47ef96770cfab0758%22%7D; Hm_lpvt_3456bee468c83cc63fb5147f119f1075=1565923258");
        params.put("Connection", "keep-alive");
        out: while(i<=1000){
            i++;
           index++;
           String handerReferer = "https://www.qichacha.com/search?key="+index;
           inner: for (char c : chars){
               handerReferer+=c;
               params.put("Referer", handerReferer);
               String url = "https://www.qichacha.com/gongsi_mindlist?type=mind&searchKey="+index+c+"&searchType=0";
               Thread.sleep(1000*1);
               Document doc = Jsoup.connect(url).headers(params).get();
               if(doc.select("span").size()>=2){
                   System.out.println(String.valueOf(index)+c+"  :  "+doc.select("span").get(1).html());
                   break inner;
               }

           }
        }
    }




    @Test
    public void demo4(){
        int a = 123455;
        System.out.println(Long.toString(a,36).toUpperCase());
        System.out.println(Long.valueOf("11111111", 36));


    }

    @Test
    public void demo5(){
        String uu = "91310114758439615P";
        System.out.printf(String.valueOf(G_Code.checkUSCC(uu)));;


    }









}
