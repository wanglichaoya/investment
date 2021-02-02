package gugu.trend.controller;

import gugu.investment.domain.IndexData;
import gugu.trend.service.IndexDatasService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class IndexDatasController {

    @Value("${server.port}")
    String port;
    @Resource
    IndexDatasService indexDatasService;


    @GetMapping("/data/{code}")
    public List<IndexData> getDatas(@PathVariable("code") String code) {
        List<IndexData> indexDatas = indexDatasService.getDatas(code);
//        if (indexDatas.isEmpty()){
//            indexDatas.add(new IndexData("redis中没有指数的数据", 0));
//        }
        return indexDatas;
    }

}
