package com.lan.order.controller;

import com.lan.common.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author：lanjy
 * @date：2020/5/12
 * @description：
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/payment/{orderId}")
    public CommonResult getPayment(@PathVariable("orderId") String orderId){
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/" + orderId, CommonResult.class);
        return responseEntity.getBody();

    }

}
