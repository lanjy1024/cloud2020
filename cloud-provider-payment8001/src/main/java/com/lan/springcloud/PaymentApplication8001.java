package com.lan.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author：lanjy
 * @date：2020/5/11
 * @description：支付模块启动类
 */
@SpringBootApplication
//@EnableEurekaClient
//@EnableDiscoveryClient
public class PaymentApplication8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication8001.class,args);
    }
}
