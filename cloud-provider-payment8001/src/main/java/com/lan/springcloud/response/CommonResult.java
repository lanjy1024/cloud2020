package com.lan.springcloud.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：lanjy
 * @date：2020/5/11
 * @description：统一响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private String code;
    private String message;
    private T date;
}
