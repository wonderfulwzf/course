package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Teacher;
import com.course.server.domain.TeacherExample;
import com.course.server.dto.TeacherDto;
import com.course.server.mapper.TeacherMapper;
import com.course.server.param.TeacherParams;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    /**
     * 返回列表
     */
    public Page<TeacherDto> list(TeacherParams teacherParams) {

        PageHelper.startPage((int)teacherParams.getPageNo(),(int)teacherParams.getPageSize());
        //查询参数
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> teacherPageInfo = new PageInfo<>(teachers);

        if(teachers==null){
            return new Page<>(teacherParams.getPageNo(),teacherParams.getPageSize());
        }
        List<TeacherDto> teacherDtos = teachers.stream().map(teacher ->
                CopierUtil.copyProperties(teacher,new TeacherDto())).collect(Collectors.toList());
        return new Page<>(teacherParams.getPageNo(),teacherParams.getPageSize(),teacherPageInfo.getTotal(),teacherDtos);
    }

    /**
     * 新增大章
     */
    public void save(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    /**
     * 更新大章
     */
    public void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }


    /**
     * 查询所有
     */
    public List<TeacherDto> all() {
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherDtos = teacherMapper.selectByExample(teacherExample);
        if(CollectionUtils.isEmpty(teacherDtos)){
            return Collections.EMPTY_LIST;
        }
        return teacherDtos.stream().map(teacher -> CopierUtil.copyProperties(teacher, new TeacherDto())).collect(Collectors.toList());
    }
}
