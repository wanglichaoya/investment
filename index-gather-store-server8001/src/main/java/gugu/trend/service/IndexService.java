package gugu.trend.service;

import gugu.investment.domain.Index;
import gugu.investment.domain.IndexData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class IndexService {

    @Resource
    RestTemplate restTemplate;

    @Cacheable(cacheNames = "indices", key = "'all_codes'", unless = "#result.isEmpty()") //根据结果，判断指数是否放入redis
    public List<Index> getOrFreshIndexesToRedis() throws ConnectException {
        String url = "http://localhost:8090/indexes/codes.json";
        List<Index> indices = restTemplate.getForObject(url, List.class);
        return indices;
    }

    @CacheEvict(cacheNames = "indices", key = "'all_codes'")
    public void cleanAllCodes() {
    }

    @Cacheable(cacheNames = "index_datas", key = "'indexData-code-'+ #p0", unless = "#result.isEmpty()")
    //根据结果，判断指数的数据是否放入redis
    public List<IndexData> getOrFreshIndexDatasToRedis(String code) throws ConnectException {
        String url = "http://localhost:8090/indexes/" + code + ".json";
        List<IndexData> indexDatas = restTemplate.getForObject(url, List.class);
        Collections.reverse(indexDatas);
        return indexDatas;
    }

    @CacheEvict(cacheNames = "index_datas", key = "'indexData-code-'+ #p0")
    public void cleanIndexDataCode(String code) {
    }


}
