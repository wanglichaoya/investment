package gugu.trend.service;

import gugu.investment.domain.IndexData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//降级服务
@Component
public class IndexDataFallbackService implements IndexDataClientService {
    @Override
    public List<IndexData> getDatas(String code) {
        return new ArrayList<>();
    }
}
