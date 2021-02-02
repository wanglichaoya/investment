package gugu.trend.service;

import gugu.investment.domain.IndexData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class IndexDatasService {

    @Cacheable(cacheNames = "index_datas", key = "'indexData-code-'+ #p0", unless = "#result.isEmpty()")
    public List<IndexData> getDatas(String code) {
        return new ArrayList<>();
    }

}
