package com.course.system.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.Resource;
import com.course.server.dto.ResourceDto;
import com.course.server.param.ResourceParams;
import com.course.server.service.ResourceService;
import com.course.server.utils.CopierUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
@RequestMapping("/admin/resource")
public class ResourceController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    public static final String BUSINESS_NAME = "资源表";

    @Autowired
    private ResourceService resourceService;

    /**
    * @description: 查询资源列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<ResourceDto>> resourceList(@RequestBody ResourceParams page){
        Rest<Page<ResourceDto>> rest = new Rest<>();
        Page<ResourceDto> list = resourceService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody String jsonStr){
        Rest rest = new Rest();
        resourceService.saveJson(jsonStr);
        return rest.resultSuccess("添加资源成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody ResourceDto resourceDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(resourceDto.getId())){
            return rest.resultSuccess("添加资源失败");
        }
        Resource resource = new Resource();
        resourceService.update(CopierUtil.copyProperties(resourceDto,resource));
        return rest.resultSuccess("添加资源成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        resourceService.delete(id);
        return rest.resultSuccess("删除资源成功");
    }

    /**
     * 资源树查询
     */
    @GetMapping("/load_tree")
    public Rest<List<ResourceDto>> loadTree() {
        Rest<List<ResourceDto>> rest = new Rest<>();
        List<ResourceDto> resourceDtoList = resourceService.loadTree();
        if(CollectionUtils.isEmpty(resourceDtoList)){
            return rest.resultFail("数据为空");
        }
        return rest.resultSuccessInfo(resourceDtoList);
    }

    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
