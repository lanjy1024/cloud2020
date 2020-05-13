package com.lan.payment.controller;

import com.lan.payment.entities.Payment;
import com.lan.common.response.CommonResult;
import com.lan.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author：lanjy
 * @date：2020/5/11
 * @description：
 */
@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        if (result > 0) {
            log.info("添加成功");
            return new CommonResult("1000","成功",result);
        }
        log.info("添加失败");
        return new CommonResult("1004","失败",result);
    }
    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        return new CommonResult("1000","查询成功",payment);
    }
}
