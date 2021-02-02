package gugu.trend.service;

import com.alibaba.fastjson.JSON;
import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import gugu.investment.domain.IndexData;
import gugu.trend.domain.TianTian;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class TianTianService {

    //    @Value("${tian.cookie}")
//    String cookie;
    @Resource
    RestTemplate restTemplate;



    @Cacheable(cacheNames = "index_datas", key = "'indexData-code-'+ #p0", unless = "#result.isEmpty()")
    public List<IndexData> getIndexDatas(String code) throws IOException {
        int i = Integer.parseInt(code.charAt(0)+"")+1;
        code = i+"."+code;
        String url = "http://push2his.eastmoney.com/api/qt/stock/kline/get?cb=jQuery112404721488538933961_1612250686963&secid=" + code + "&ut=fa5fd1943c7b386f172d6893dbfba10b&fields1=f1%2Cf2%2Cf3%2Cf4%2Cf5&fields2=f51%2Cf52%2Cf53%2Cf54%2Cf55%2Cf56%2Cf57%2Cf58&klt=101&fqt=0&beg=19900101&end=20220101&_=1612250686980";
        URL url1 = new URL(url);
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 从响应模型中获取响应实体
        org.apache.http.HttpEntity responseEntity = response.getEntity();
        String str;
        TianTian tian = null;
        List<IndexData> indexData = null;
        if (responseEntity != null) {
            str = EntityUtils.toString(responseEntity);
            int start = str.indexOf('(') + 1;
            int end = str.indexOf(')');
            str = str.substring(start, end);
            tian = JSON.parseObject(str, TianTian.class);
            indexData = TianTianToIndexDatas(tian);
        }

        return indexData;
    }


    public List<IndexData> TianTianToIndexDatas(TianTian tian) {
        List<String> klines = tian.getData().getKlines();
        List<IndexData> indexData = new ArrayList<>();
        for (String str : klines) {
            String[] data = str.split(",");
            float closePoint = Float.parseFloat(data[2]);
            indexData.add(new IndexData(data[0], closePoint));
        }
        return indexData;
    }


}
