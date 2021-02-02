package gugu.trend.controller;

import gugu.investment.domain.Index;
import gugu.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class IndexController {

    @Value("${server.port}")
    String port;
    @Resource
    IndexService indexService;

    @CrossOrigin //支持跨域访问
    @GetMapping("/codes")
    public List<Index> codes() {
        List<Index> indexes = indexService.getIndexes();
//        if (indexes.isEmpty()) {
//            indexes.add(new Index("0", "redis中没有指数"));
//            return indexes;
//        }
        return indexes;
    }

}
