package com.lan.payment.dao;

import com.lan.payment.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author：lanjy
 * @date：2020/5/11
 * @description：
 */
@Mapper
public interface PaymentDao {

    int create(Payment payment);


    Payment getPaymentById(@Param("id") Long id);
}
