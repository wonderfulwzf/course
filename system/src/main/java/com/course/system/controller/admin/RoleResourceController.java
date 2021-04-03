package com.course.system.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.RoleResource;
import com.course.server.dto.RoleResourceDto;
import com.course.server.param.RoleResourceParams;
import com.course.server.service.RoleResourceService;
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
@RequestMapping("/admin/roleResource")
public class RoleResourceController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(RoleResourceController.class);

    public static final String BUSINESS_NAME = "角色资源表";

    @Autowired
    private RoleResourceService roleResourceService;

    /**
    * @description: 查询角色资源列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<RoleResourceDto>> roleResourceList(@RequestBody RoleResourceParams page){
        Rest<Page<RoleResourceDto>> rest = new Rest<>();
        Page<RoleResourceDto> list = roleResourceService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody RoleResourceDto roleResourceDto){
        Rest rest = new Rest();
        roleResourceDto.setId(UuidUtil.getShortUuid());
        RoleResource roleResource = new RoleResource();
        roleResourceService.save(CopierUtil.copyProperties(roleResourceDto,roleResource));
        return rest.resultSuccess("添加角色资源成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody RoleResourceDto roleResourceDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(roleResourceDto.getId())){
            return rest.resultSuccess("添加角色资源失败");
        }
        RoleResource roleResource = new RoleResource();
        roleResourceService.update(CopierUtil.copyProperties(roleResourceDto,roleResource));
        return rest.resultSuccess("添加角色资源成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        roleResourceService.delete(id);
        return rest.resultSuccess("删除角色资源成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
