package com.course.business.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.Category;
import com.course.server.dto.CategoryDto;
import com.course.server.param.CategoryParams;
import com.course.server.service.CategoryService;
import com.course.server.utils.CopierUtil;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    public static final String BUSINESS_NAME = "种类";

    @Autowired
    private CategoryService categoryService;

    /**
    * @description: 查询分类列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<CategoryDto>> categoryList(@RequestBody CategoryParams page){
        Rest<Page<CategoryDto>> rest = new Rest<>();
        Page<CategoryDto> list = categoryService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    /**
     * @description: 查询所有
     * @author wangzhifang
     * @createTime：2021/3/12 20:28
     */
    @RequestMapping("/all")
    public Rest<List<CategoryDto>> all(){
        Rest<List<CategoryDto>> rest = new Rest<>();
       return rest.resultSuccessInfo(categoryService.all());
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody CategoryDto categoryDto){
        Rest rest = new Rest();
        categoryDto.setId(UuidUtil.getShortUuid());
        Category category = new Category();
        categoryService.save(CopierUtil.copyProperties(categoryDto,category));
        return rest.resultSuccess("添加分类成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody CategoryDto categoryDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(categoryDto.getId())){
            return rest.resultSuccess("添加分类失败");
        }
        Category category = new Category();
        categoryService.update(CopierUtil.copyProperties(categoryDto,category));
        return rest.resultSuccess("添加分类成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        categoryService.delete(id);
        return rest.resultSuccess("删除分类成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
