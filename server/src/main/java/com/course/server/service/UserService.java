package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.User;
import com.course.server.domain.UserExample;
import com.course.server.dto.UserDto;
import com.course.server.mapper.UserMapper;
import com.course.server.param.UserParams;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 返回列表
     */
    public Page<UserDto> list(UserParams userParams) {

        PageHelper.startPage((int)userParams.getPageNo(),(int)userParams.getPageSize());
        //查询参数
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> userPageInfo = new PageInfo<>(users);

        if(users==null){
            return new Page<>(userParams.getPageNo(),userParams.getPageSize());
        }
        List<UserDto> userDtos = users.stream().map(user ->
                CopierUtil.copyProperties(user,new UserDto())).collect(Collectors.toList());
        return new Page<>(userParams.getPageNo(),userParams.getPageSize(),userPageInfo.getTotal(),userDtos);
    }

    /**
     * 新增大章
     */
    public void save(User user) {
        userMapper.insert(user);
    }

    /**
     * 更新大章
     */
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }


}
