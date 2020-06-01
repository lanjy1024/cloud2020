package com.lan.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author：lanjy
 * @date：2020/5/31
 * @description：服务注册到zookeeper的案例
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
@EnableDiscoveryClient
public class PaymentApplicationZk8004 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplicationZk8004.class,args);
    }
}
