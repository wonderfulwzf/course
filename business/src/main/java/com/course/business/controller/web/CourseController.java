package com.course.business.controller.web;

import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.dto.CourseDto;
import com.course.server.param.CourseParams;
import com.course.server.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("webCourseController")
@RequestMapping("/web/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    /**
     * 列表查询，查询最新的3门已发布的课程
     */
    @GetMapping("/list_new")
    public Rest<List<CourseDto>> listNew() {
        Rest<List<CourseDto>> rest = new Rest<>();

        CourseParams courseParams = new CourseParams();
        courseParams.setPageNo(1);
        courseParams.setPageSize(3);
        List<CourseDto> courseDtoList = courseService.listNew(courseParams);

        return rest.resultSuccessInfo(courseDtoList);
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public Rest list(@RequestBody CourseParams courseParams) {
        Rest rest = new Rest();
        courseParams.setStatus("P");
        Page<CourseDto> list = courseService.list(courseParams);
        return rest.resultSuccessInfo(list);
    }

    //@GetMapping("/find/{id}")
    //public ResponseDto findCourse(@PathVariable String id) {
    //    LOG.info("查找课程开始：{}", id);
    //    ResponseDto responseDto = new ResponseDto();
    //    CourseDto courseDto = courseService.findCourse(id);
    //    responseDto.setContent(courseDto);
    //    LOG.info("查找课程结束：{}", responseDto);
    //    return responseDto;
    //}
}
