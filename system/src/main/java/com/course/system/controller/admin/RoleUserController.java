package com.course.system.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.RoleUser;
import com.course.server.dto.RoleUserDto;
import com.course.server.param.RoleUserParams;
import com.course.server.service.RoleUserService;
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
@RequestMapping("/admin/roleUser")
public class RoleUserController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(RoleUserController.class);

    public static final String BUSINESS_NAME = "角色用户关系表";

    @Autowired
    private RoleUserService roleUserService;

    /**
    * @description: 查询角色用户列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<RoleUserDto>> roleUserList(@RequestBody RoleUserParams page){
        Rest<Page<RoleUserDto>> rest = new Rest<>();
        Page<RoleUserDto> list = roleUserService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody RoleUserDto roleUserDto){
        Rest rest = new Rest();
        roleUserDto.setId(UuidUtil.getShortUuid());
        RoleUser roleUser = new RoleUser();
        roleUserService.save(CopierUtil.copyProperties(roleUserDto,roleUser));
        return rest.resultSuccess("添加角色用户成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody RoleUserDto roleUserDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(roleUserDto.getId())){
            return rest.resultSuccess("添加角色用户失败");
        }
        RoleUser roleUser = new RoleUser();
        roleUserService.update(CopierUtil.copyProperties(roleUserDto,roleUser));
        return rest.resultSuccess("添加角色用户成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        roleUserService.delete(id);
        return rest.resultSuccess("删除角色用户成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
