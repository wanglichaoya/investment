package gugu.trend.controller;

import gugu.investment.domain.IndexData;
import gugu.trend.domain.TianTian;
import gugu.trend.service.TianTianService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
public class TianTianController {
    @Resource
    TianTianService tianTianService;

    @GetMapping("/getDatas/{code}")
    public List<IndexData> getTianTianDatas(@PathVariable("code")String code) throws IOException {
        List<IndexData> indexDatas = tianTianService.getIndexDatas(code);
        return indexDatas;
    }


}
