package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Course;
import com.course.server.domain.CourseExample;
import com.course.server.dto.CourseDto;
import com.course.server.mapper.CourseMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.param.CourseParams;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class CourseService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private MyCourseMapper myCourseMapper;


    /**
     * 返回列表
     */
    public Page<CourseDto> list(CourseParams courseParams) {

        PageHelper.startPage((int)courseParams.getPageNo(),(int)courseParams.getPageSize());
        //查询参数
        CourseExample courseExample = new CourseExample();
        List<Course> courses = courseMapper.selectByExample(courseExample);
        PageInfo<Course> coursePageInfo = new PageInfo<>(courses);

        if(courses==null){
            return new Page<>(courseParams.getPageNo(),courseParams.getPageSize());
        }
        List<CourseDto> courseDtos = courses.stream().map(course ->
                CopierUtil.copyProperties(course,new CourseDto())).collect(Collectors.toList());
        return new Page<>(courseParams.getPageNo(),courseParams.getPageSize(),coursePageInfo.getTotal(),courseDtos);
    }

    /**
     * 新增大章
     */
    public void save(Course course) {
        course.setCreatedAt(new Date());
        course.setUpdatedAt(new Date());
        courseMapper.insert(course);
    }

    /**
     * 更新大章
     */
    public void update(Course course) {
        course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    /**
     *  更新课程时长
     */
    public void updateTime(String courseId){
        LOG.info("更新课程时长:{}",courseId);
        myCourseMapper.updateTime(courseId);
    }

}
