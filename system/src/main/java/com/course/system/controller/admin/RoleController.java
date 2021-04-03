package com.course.system.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.Role;
import com.course.server.dto.RoleDto;
import com.course.server.param.RoleParams;
import com.course.server.service.RoleService;
import com.course.server.utils.CopierUtil;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
@RequestMapping("/admin/role")
public class RoleController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    public static final String BUSINESS_NAME = "角色表";

    @Autowired
    private RoleService roleService;

    /**
    * @description: 查询大章列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<RoleDto>> roleList(@RequestBody RoleParams page){
        Rest<Page<RoleDto>> rest = new Rest<>();
        Page<RoleDto> list = roleService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody RoleDto roleDto){
        Rest rest = new Rest();
        roleDto.setId(UuidUtil.getShortUuid());
        Role role = new Role();
        roleService.save(CopierUtil.copyProperties(roleDto,role));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody RoleDto roleDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(roleDto.getId())){
            return rest.resultSuccess("添加大章失败");
        }
        Role role = new Role();
        roleService.update(CopierUtil.copyProperties(roleDto,role));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        roleService.delete(id);
        return rest.resultSuccess("删除大章成功");
    }

    @RequestMapping("/save_resource")
    public Rest saveResource(@RequestBody RoleDto roleDto){
        LOG.info("保存角色资源关联开始");
        Rest<RoleDto> rest = new Rest<>();
        roleService.saveResource(roleDto);
        return rest;
    }

    /**
     * 加载已关联的资源
     */
    @GetMapping("/list_resource/{roleId}")
    public Rest<List<String>> listResource(@PathVariable String roleId) {
        LOG.info("加载资源开始");
        Rest<List<String>> rest = new Rest<>();
        List<String> resourceIdList = roleService.listResource(roleId);
        return rest.resultSuccessInfo(resourceIdList);
    }


    /**
     * 保存用户
     * @param roleDto
     */
    @PostMapping("/save_user")
    public Rest saveUser(@RequestBody RoleDto roleDto) {
        LOG.info("保存角色用户关联开始");
        Rest<RoleDto> rest = new Rest<>();
        roleService.saveUser(roleDto);
        return rest.resultSuccessInfo(roleDto);
    }

    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
