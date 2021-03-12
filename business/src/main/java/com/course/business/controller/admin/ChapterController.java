package com.course.business.controller.admin;


import com.course.server.common.Page;
import com.course.server.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    @Autowired
    private ChapterService chapterService;


    @RequestMapping("/list")
    public Page chapterList(@RequestBody Page page){
        chapterService.list(page);
        return page;
    }
}
