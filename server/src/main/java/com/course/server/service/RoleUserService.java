package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.RoleUser;
import com.course.server.domain.RoleUserExample;
import com.course.server.dto.RoleUserDto;
import com.course.server.mapper.RoleUserMapper;
import com.course.server.param.RoleUserParams;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王智芳
 * @data 2021/3/6 14:38
 */
@Service
public class RoleUserService {

    @Autowired
    private RoleUserMapper roleUserMapper;


    /**
     * 返回列表
     */
    public Page<RoleUserDto> list(RoleUserParams roleUserParams) {

        PageHelper.startPage((int)roleUserParams.getPageNo(),(int)roleUserParams.getPageSize());
        //查询参数
        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> roleUserPageInfo = new PageInfo<>(roleUsers);

        if(roleUsers==null){
            return new Page<>(roleUserParams.getPageNo(),roleUserParams.getPageSize());
        }
        List<RoleUserDto> roleUserDtos = roleUsers.stream().map(roleUser ->
                CopierUtil.copyProperties(roleUser,new RoleUserDto())).collect(Collectors.toList());
        return new Page<>(roleUserParams.getPageNo(),roleUserParams.getPageSize(),roleUserPageInfo.getTotal(),roleUserDtos);
    }

    /**
     * 新增大章
     */
    public void save(RoleUser roleUser) {
        roleUserMapper.insert(roleUser);
    }

    /**
     * 更新大章
     */
    public void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }
}
