package gugu.trend.controller;

import gugu.investment.domain.Index;
import gugu.investment.domain.IndexData;
import gugu.trend.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class IndexController {

    @Resource
    IndexService indexService;

    @GetMapping("/freshCodes")
    public List<Index> getCodes() {
        List<Index> indices;
        try {
            indices = indexService.getOrFreshIndexesToRedis();
        } catch (Exception e) {
            indices = new ArrayList<>();
            indices.add(new Index("0", "第三方数据采集错误"));
            log.warn("第三方数据采集错误");
        }
        return indices;
    }


    @GetMapping("/freshIndexDatas/{code}")
    public List<IndexData> getIndexDatas(@PathVariable("code") String code) {
        List<IndexData> indexDatas;
        try {
            indexDatas = indexService.getOrFreshIndexDatasToRedis(code);
        } catch (Exception e) {
            indexDatas = new ArrayList<>();
            indexDatas.add(new IndexData("第三方数据采集错误", 0));
            log.warn("第三方数据采集错误");
        }
        return indexDatas;
    }

    @GetMapping("/cleanAllCodes")
    public String cleanAllIndex() {
        indexService.cleanAllCodes();
        return "success";
    }

    @GetMapping("/cleanIndexDatas/{code}")
    public String cleanIndexDatas(@PathVariable("code") String code) {
        indexService.cleanIndexDataCode(code);
        return "success";

    }


}
