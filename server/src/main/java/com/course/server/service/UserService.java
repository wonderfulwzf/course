package com.course.server.service;


import com.alibaba.fastjson.JSON;
import com.course.server.common.Page;
import com.course.server.domain.User;
import com.course.server.domain.UserExample;
import com.course.server.dto.ResourceDto;
import com.course.server.dto.UserDto;
import com.course.server.mapper.UserMapper;
import com.course.server.mapper.my.MyUserMapper;
import com.course.server.param.UserParams;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyUserMapper myUserMapper;


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
        user.setPassword(null);
        userMapper.updateByPrimaryKeySelective(user);
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

    /**
     * 重置密码
     * @param userDto
     */
    public void savePassword(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 登录
     * @param userDto
     */
    public UserDto  login(UserDto userDto) {
        User user = selectByLoginName(userDto.getLoginName());
        if(user == null){
            //用户不存在
        }else {
            if(user.getPassword().equals(userDto.getPassword())){
                //登录成功
                UserDto userDto1= new UserDto();
                CopierUtil.copyProperties(user,userDto1);
                setAuth(userDto1);
                return userDto1;
            }else {
                //登录失败
            }
        }
        return null;
    }

    /**
     * 为登录用户读取权限
     */
    private void setAuth(UserDto loginUserDto) {
        List<ResourceDto> resourceDtoList = myUserMapper.findResources(loginUserDto.getId());
        loginUserDto.setResources(resourceDtoList);

        // 整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(resourceDtoList)) {
            for (int i = 0, l = resourceDtoList.size(); i < l; i++) {
                ResourceDto resourceDto = resourceDtoList.get(i);
                String arrayString = resourceDto.getRequest();
                List<String> requestList = JSON.parseArray(arrayString, String.class);
                if (!CollectionUtils.isEmpty(requestList)) {
                    requestSet.addAll(requestList);
                }
            }
        }
        LOG.info("有权限的请求：{}", requestSet);
        loginUserDto.setRequests(requestSet);
    }
}
