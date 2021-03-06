package com.course.system.controller;


import com.course.server.domain.Test;
import com.course.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public List<Test> list(){
        return testService.list();
    }
}
