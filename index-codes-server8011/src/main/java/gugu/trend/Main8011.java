package gugu.trend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//指数代码服务

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class Main8011 {
    public static void main(String[] args) {
        SpringApplication.run(Main8011.class, args);
    }
}
