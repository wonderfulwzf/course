package com.course.business.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.Teacher;
import com.course.server.dto.TeacherDto;
import com.course.server.param.TeacherParams;
import com.course.server.service.TeacherService;
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
@RequestMapping("/admin/teacher")
public class TeacherController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);

    public static final String BUSINESS_NAME = "老师";

    @Autowired
    private TeacherService teacherService;

    /**
    * @description: 查询大章列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<TeacherDto>> teacherList(@RequestBody TeacherParams page){
        Rest<Page<TeacherDto>> rest = new Rest<>();
        Page<TeacherDto> list = teacherService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody TeacherDto teacherDto){
        Rest rest = new Rest();
        teacherDto.setId(UuidUtil.getShortUuid());
        Teacher teacher = new Teacher();
        teacherService.save(CopierUtil.copyProperties(teacherDto,teacher));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody TeacherDto teacherDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(teacherDto.getId())){
            return rest.resultSuccess("添加大章失败");
        }
        Teacher teacher = new Teacher();
        teacherService.update(CopierUtil.copyProperties(teacherDto,teacher));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        teacherService.delete(id);
        return rest.resultSuccess("删除大章成功");
    }

    @RequestMapping("/all")
    public Rest<List<TeacherDto>> all(){
        Rest<List<TeacherDto>> rest = new Rest<>();
        return rest.resultSuccessInfo(teacherService.all());
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
