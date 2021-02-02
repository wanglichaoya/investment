package gugu.trend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//指数代码的数据服务
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class Main8021 {
    public static void main(String[] args) {
        SpringApplication.run(Main8021.class,args);
    }
}
