package com.course.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

//    @ExceptionHandler(value = ValidatorException.class)
//    @ResponseBody
//    public Rest validatorExceptionHandler(ValidatorException e) {
//        Rest rest = new Rest();
//        rest.setSuccess(false);
//        LOG.warn(e.getMessage());
//        rest.setMessage("请求参数异常！");
//        return rest;
//    }

//    @ExceptionHandler(value = BusinessException.class)
//    @ResponseBody
//    public Rest businessExceptionHandler(BusinessException e) {
//        Rest rest = new Rest();
//        rest.setSuccess(false);
//        LOG.error("业务异常：{}", e.getCode().getDesc());
//        rest.setMessage(e.getCode().getDesc());
//        return rest;
//    }
}
