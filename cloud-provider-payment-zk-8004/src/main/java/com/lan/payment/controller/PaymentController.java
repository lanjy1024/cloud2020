package com.lan.payment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：lanjy
 * @date：2020/5/31
 * @description：
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String SERVER_PORT;


    @GetMapping("/zk")
    public String payment(){
        log.info("调用zk的服务成功了=========");
        return "调用zk的服务成功了,返回的服务端口:"+SERVER_PORT;
    }
}
