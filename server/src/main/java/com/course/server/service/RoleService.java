package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Role;
import com.course.server.domain.RoleExample;
import com.course.server.domain.RoleResource;
import com.course.server.domain.RoleResourceExample;
import com.course.server.dto.RoleDto;
import com.course.server.mapper.RoleMapper;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.param.RoleParams;
import com.course.server.utils.CopierUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private RoleResourceMapper roleResourceMapper;


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


    /**
     * 按角色保存资源
     */
    public void saveResource(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> resourceIds = roleDto.getResourceIds();
        // 清空库中所有的当前角色下的记录
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(example);

        // 保存角色资源
        for (int i = 0; i < resourceIds.size(); i++) {
            RoleResource roleResource = new RoleResource();
            roleResource.setId(UuidUtil.getShortUuid());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceIds.get(i));
            roleResourceMapper.insert(roleResource);
        }
    }

    /**
     * 按角色加载资源
     * @param roleId
     */
    public List<String> listResource(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(example);
        List<String> resourceIdList = new ArrayList<>();
        for (int i = 0, l = roleResourceList.size(); i < l; i++) {
            resourceIdList.add(roleResourceList.get(i).getResourceId());
        }
        return resourceIdList;
    }
}
