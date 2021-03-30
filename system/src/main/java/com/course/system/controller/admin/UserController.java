package com.course.system.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.User;
import com.course.server.dto.UserDto;
import com.course.server.param.UserParams;
import com.course.server.service.UserService;
import com.course.server.utils.CopierUtil;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
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
@RequestMapping("/admin/user")
public class UserController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String BUSINESS_NAME = "";

    @Autowired
    private UserService userService;

    /**
    * @description: 查询大章列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<UserDto>> userList(@RequestBody UserParams page){
        Rest<Page<UserDto>> rest = new Rest<>();
        Page<UserDto> list = userService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody UserDto userDto){
        Rest rest = new Rest();
        userDto.setId(UuidUtil.getShortUuid());
        //密码加密
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        User userDB = userService.selectByLoginName(userDto.getLoginName());
        if(userDB != null){
            return rest.resultFail("用户名不能为空");
        }
        User user = new User();
        userService.save(CopierUtil.copyProperties(userDto,user));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody UserDto userDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(userDto.getId())){
            return rest.resultSuccess("添加大章失败");
        }
        User user = new User();
        userService.update(CopierUtil.copyProperties(userDto,user));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        userService.delete(id);
        return rest.resultSuccess("删除大章成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
