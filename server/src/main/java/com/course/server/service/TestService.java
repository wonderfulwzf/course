package com.course.server.service;


import com.course.server.domain.Test;
import com.course.server.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    /**
     * 返回列表
     */
    public List<Test> list() {
        return testMapper.list();
    }
}
