package com.lan.order.controller;

import com.lan.common.response.CommonResult;
import com.lan.order.service.PaymentFeignService;
import com.lan.payment.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：lanjy
 * @date：2020/6/1
 * @description：
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/payment/{orderId}")
    public CommonResult getPayment(@PathVariable("orderId") String orderId){
        CommonResult<Payment> commonResult = paymentFeignService.getPayment(orderId);

        commonResult.setMessage(commonResult.getMessage()+",通过feign调用");

        return commonResult;
    }

}
