package gugu.trend.service;

import gugu.investment.domain.IndexData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
@FeignClient(name = "index-data-server", fallback = IndexDataFallbackService.class)
public interface IndexDataClientService {

    @GetMapping("/data/{code}")
    List<IndexData> getDatas(@PathVariable("code") String code); //获取源数据
}
