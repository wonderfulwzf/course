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
@RequestMapping("/admin/chapter")
public class ChapterController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    public static final String BUSINESS_NAME = "大章";

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
        Page<ChapterDto> list = chapterService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody ChapterDto chapterDto){
        Rest rest = new Rest();
        chapterDto.setId(UuidUtil.getShortUuid());
        Chapter chapter = new Chapter();
        chapterService.save(CopierUtil.copyProperties(chapterDto,chapter));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody ChapterDto chapterDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(chapterDto.getId())){
            return rest.resultSuccess("添加大章失败");
        }
        Chapter chapter = new Chapter();
        chapterService.update(CopierUtil.copyProperties(chapterDto,chapter));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        chapterService.delete(id);
        return rest.resultSuccess("删除大章成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "456";
    }
}
