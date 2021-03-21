package com.course.server.service;


import com.course.server.domain.CourseCategory;
import com.course.server.domain.CourseCategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.CourseCategoryDto;
import com.course.server.mapper.CourseCategoryMapper;
import com.course.server.utils.CopierUtil;
import com.course.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 新增种类
     */
    public void save(CourseCategory courseCategory) {
        courseCategoryMapper.insert(courseCategory);
    }

    /**
     * 更新种类
     */
    public void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }
    /**
     * 删除种类
     */
    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量保存种类
     */
    @Transactional
    public void saveBatch(String courseId, List<CategoryDto> categoryDtos){
        //先删除再新增
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
        for(int i = 0 ;i < categoryDtos.size(); i++){
            CategoryDto categoryDto = categoryDtos.get(i);
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(categoryDto.getId());
            save(courseCategory);
        }
    }

    /**
     * 根据课程id查找所有分类
     * @param courseId
     * @return
     */
    public List<CourseCategoryDto> listByCourseId(String courseId){
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategories = courseCategoryMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(courseCategories)){
            return Collections.EMPTY_LIST;
        }
        return courseCategories.stream().map(ourseCategory->
                CopierUtil.copyProperties(ourseCategory,new CourseCategoryDto())).collect(Collectors.toList());
    }

}
