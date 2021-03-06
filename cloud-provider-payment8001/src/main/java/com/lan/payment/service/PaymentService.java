package com.lan.payment.service;

import com.lan.payment.dao.PaymentDao;
import com.lan.payment.entities.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：lanjy
 * @date：2020/5/11
 * @description：
 */

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    public int create(Payment payment){
        return paymentDao.create(payment);
    }


    public Payment getPaymentById(@Param("id") Long id){
        return paymentDao.getPaymentById(id);
    }
}
