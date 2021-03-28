package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Section;
import com.course.server.domain.SectionExample;
import com.course.server.dto.SectionDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.param.SectionParams;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class SectionService {

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private CourseService courseService;


    /**
     * 返回列表
     */
    public Page<SectionDto> list(SectionParams sectionParams) {

        PageHelper.startPage((int)sectionParams.getPageNo(),(int)sectionParams.getPageSize());
        //查询参数
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        //对课程id判空
        if (!StringUtils.isEmpty(sectionParams.getCourseId())) {
            criteria.andCourseIdEqualTo(sectionParams.getCourseId());
        }
        //对大章id判空
        if (!StringUtils.isEmpty(sectionParams.getChapterId())) {
            criteria.andChapterIdEqualTo(sectionParams.getChapterId());
        }
        List<Section> sections = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> sectionPageInfo = new PageInfo<>(sections);
        if(sections==null){
            return new Page<>(sectionParams.getPageNo(),sectionParams.getPageSize());
        }
        List<SectionDto> sectionDtos = sections.stream().map(section ->
                CopierUtil.copyProperties(section,new SectionDto())).collect(Collectors.toList());
        return new Page<>(sectionParams.getPageNo(),sectionParams.getPageSize(),sectionPageInfo.getTotal(),sectionDtos);
    }

    /**
     * 新增大章
     */
    @Transactional
    public void save(Section section)  {
        section.setCreatedAt(new Date());
        section.setUpdatedAt(new Date());
        sectionMapper.insert(section);
        courseService.updateTime(section.getCourseId());
    }

    /**
     * 更新大章
     */
    @Transactional
    public void update(Section section) {
        section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);
        courseService.updateTime(section.getCourseId());
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
