package com.course.server.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.course.server.domain.Test;
import com.course.server.mapper.TestMapper;
import com.course.server.service.TestService;

import java.util.List;

/**
 * @author 王智芳
 * @data 2021/3/6 14:39
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> list() {
        return testMapper.list();
    }
}
