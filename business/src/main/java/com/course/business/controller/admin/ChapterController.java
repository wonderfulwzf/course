package com.course.business.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.param.ChapterParams;
import com.course.server.service.ChapterService;
import com.course.server.utils.CopierUtil;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public Rest<Page<ChapterDto>> chapterList(@RequestBody ChapterParams page){
        Rest<Page<ChapterDto>> rest = new Rest<>();
        Page<ChapterDto> voPage = new Page<>();
        Page<Chapter> list = chapterService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getCurrentPage(),page.getPageSize()));
        }
        List<ChapterDto> collect = list.getRecords().stream().map(chapter -> {
            ChapterDto chapterDto = new ChapterDto();
            return CopierUtil.copyProperties(chapter, chapterDto);
        }).collect(Collectors.toList());
        voPage.setRecords(collect);
        voPage.setPageSize(page.getPageSize());
        voPage.setCurrentPage(page.getCurrentPage());
        voPage.setTotalRecord(list.getTotalRecord());
        voPage.setTotalPage(list.getTotalPage());
        return rest.resultSuccessInfo(voPage);
    }

    @RequestMapping("/save")
    public Rest chapterList(@RequestBody ChapterDto chapterDto){
        Rest rest = new Rest();
        chapterDto.setId(UuidUtil.getShortUuid());
        chapterService.save(chapterDto);
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
