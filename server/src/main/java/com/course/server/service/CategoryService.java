package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Category;
import com.course.server.domain.CategoryExample;
import com.course.server.dto.CategoryDto;
import com.course.server.mapper.CategoryMapper;
import com.course.server.param.CategoryParams;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 返回列表
     */
    public Page<CategoryDto> list(CategoryParams categoryParams) {

        PageHelper.startPage((int)categoryParams.getPageNo(),(int)categoryParams.getPageSize());
        //查询参数
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categorys);

        if(categorys==null){
            return new Page<>(categoryParams.getPageNo(),categoryParams.getPageSize());
        }
        List<CategoryDto> categoryDtos = categorys.stream().map(category ->
                CopierUtil.copyProperties(category,new CategoryDto())).collect(Collectors.toList());
        return new Page<>(categoryParams.getPageNo(),categoryParams.getPageSize(),categoryPageInfo.getTotal(),categoryDtos);
    }

    /**
     * 新增大章
     */
    public void save(Category category) {
        categoryMapper.insert(category);
    }

    /**
     * 更新大章
     */
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
    /**
     * 删除大章
     */
    @Transactional
    public void delete(String id) {
        //判断是不是一级分类 是的话删除其子类
        deletechildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    private void deletechildren(String id){
        Category category = categoryMapper.selectByPrimaryKey(id);
        if("00000000".equals(category.getParent())){
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(categoryExample);
        }
    }

    /**
     * 查询所有
     */
    public List<CategoryDto> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categorys = categoryMapper.selectByExample(categoryExample);
        if(CollectionUtils.isEmpty(categorys)){
            return Collections.EMPTY_LIST;
        }
        return categorys.stream().map(category -> CopierUtil.copyProperties(category, new CategoryDto())).collect(Collectors.toList());
    }
}
