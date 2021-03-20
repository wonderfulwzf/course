package com.course.business.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.Course;
import com.course.server.dto.CourseDto;
import com.course.server.param.CourseParams;
import com.course.server.service.CourseService;
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

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
@RequestMapping("/admin/course")
public class CourseController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);

    public static final String BUSINESS_NAME = "课程";

    @Autowired
    private CourseService courseService;

    /**
    * @description: 查询课程列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<CourseDto>> courseList(@RequestBody CourseParams page){
        Rest<Page<CourseDto>> rest = new Rest<>();
        Page<CourseDto> list = courseService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody CourseDto courseDto){
        Rest rest = new Rest();
        courseDto.setId(UuidUtil.getShortUuid());
        Course course = new Course();
        courseService.save(CopierUtil.copyProperties(courseDto,course));
        return rest.resultSuccess("添加课程成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody CourseDto courseDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(courseDto.getId())){
            return rest.resultSuccess("添加课程失败");
        }
        Course course = new Course();
        courseService.update(CopierUtil.copyProperties(courseDto,course));
        return rest.resultSuccess("添加课程成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        courseService.delete(id);
        return rest.resultSuccess("删除课程成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
