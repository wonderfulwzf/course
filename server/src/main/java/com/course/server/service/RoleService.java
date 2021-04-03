package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Role;
import com.course.server.domain.RoleExample;
import com.course.server.dto.RoleDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.param.RoleParams;
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
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;


    /**
     * 返回列表
     */
    public Page<RoleDto> list(RoleParams roleParams) {

        PageHelper.startPage((int)roleParams.getPageNo(),(int)roleParams.getPageSize());
        //查询参数
        RoleExample roleExample = new RoleExample();
        List<Role> roles = roleMapper.selectByExample(roleExample);
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);

        if(roles==null){
            return new Page<>(roleParams.getPageNo(),roleParams.getPageSize());
        }
        List<RoleDto> roleDtos = roles.stream().map(role ->
                CopierUtil.copyProperties(role,new RoleDto())).collect(Collectors.toList());
        return new Page<>(roleParams.getPageNo(),roleParams.getPageSize(),rolePageInfo.getTotal(),roleDtos);
    }

    /**
     * 新增大章
     */
    public void save(Role role) {
        roleMapper.insert(role);
    }

    /**
     * 更新大章
     */
    public void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }
}
