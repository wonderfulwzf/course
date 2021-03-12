package com.course.business.controller.admin;


import com.course.server.common.Page;
import com.course.server.dto.ChapterDto;
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

    /**
    * @description: 查询大章列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Page chapterList(@RequestBody Page page){
        chapterService.list(page);
        return page;
    }

    @RequestMapping("/add")
    public void chapterList(@RequestBody ChapterDto chapterDto){
        chapterDto.setId("100000000");
        chapterService.add(chapterDto);
    }
}
