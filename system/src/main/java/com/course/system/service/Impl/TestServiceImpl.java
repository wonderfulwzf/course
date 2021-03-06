package com.course.system.service.Impl;

import com.course.system.domain.Test;
import com.course.system.mapper.TestMapper;
import com.course.system.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
