package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.RoleResource;
import com.course.server.domain.RoleResourceExample;
import com.course.server.dto.RoleResourceDto;
import com.course.server.mapper.RoleResourceMapper;
import com.course.server.param.RoleResourceParams;
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
public class RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;


    /**
     * 返回列表
     */
    public Page<RoleResourceDto> list(RoleResourceParams roleResourceParams) {

        PageHelper.startPage((int)roleResourceParams.getPageNo(),(int)roleResourceParams.getPageSize());
        //查询参数
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        PageInfo<RoleResource> roleResourcePageInfo = new PageInfo<>(roleResources);

        if(roleResources==null){
            return new Page<>(roleResourceParams.getPageNo(),roleResourceParams.getPageSize());
        }
        List<RoleResourceDto> roleResourceDtos = roleResources.stream().map(roleResource ->
                CopierUtil.copyProperties(roleResource,new RoleResourceDto())).collect(Collectors.toList());
        return new Page<>(roleResourceParams.getPageNo(),roleResourceParams.getPageSize(),roleResourcePageInfo.getTotal(),roleResourceDtos);
    }

    /**
     * 新增大章
     */
    public void save(RoleResource roleResource) {
        roleResourceMapper.insert(roleResource);
    }

    /**
     * 更新大章
     */
    public void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }
}
