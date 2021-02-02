package gugu.trend.service;

import gugu.investment.domain.Index;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class IndexService {

    @Cacheable(cacheNames = "indices", key = "'all_codes'", unless = "#result.isEmpty()")
    public List<Index> getIndexes() {
        return new ArrayList<>();
    }

}
