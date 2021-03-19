package com.course.business.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.Section;
import com.course.server.dto.SectionDto;
import com.course.server.param.SectionParams;
import com.course.server.service.SectionService;
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
@RequestMapping("/admin/section")
public class SectionController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);

    public static final String BUSINESS_NAME = "小节";

    @Autowired
    private SectionService sectionService;

    /**
    * @description: 查询小节列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<SectionDto>> sectionList(@RequestBody SectionParams page){
        Rest<Page<SectionDto>> rest = new Rest<>();
        Page<SectionDto> list = sectionService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody SectionDto sectionDto){
        Rest rest = new Rest();
        sectionDto.setId(UuidUtil.getShortUuid());
        Section section = new Section();
        sectionService.save(CopierUtil.copyProperties(sectionDto,section));
        return rest.resultSuccess("添加小节成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody SectionDto sectionDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(sectionDto.getId())){
            return rest.resultSuccess("添加小节失败");
        }
        Section section = new Section();
        sectionService.update(CopierUtil.copyProperties(sectionDto,section));
        return rest.resultSuccess("添加小节成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        sectionService.delete(id);
        return rest.resultSuccess("删除小节成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
