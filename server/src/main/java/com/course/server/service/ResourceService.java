package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Resource;
import com.course.server.domain.ResourceExample;
import com.course.server.dto.ResourceDto;
import com.course.server.mapper.ResourceMapper;
import com.course.server.param.ResourceParams;
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
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;


    /**
     * 返回列表
     */
    public Page<ResourceDto> list(ResourceParams resourceParams) {

        PageHelper.startPage((int)resourceParams.getPageNo(),(int)resourceParams.getPageSize());
        //查询参数
        ResourceExample resourceExample = new ResourceExample();
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        PageInfo<Resource> resourcePageInfo = new PageInfo<>(resources);

        if(resources==null){
            return new Page<>(resourceParams.getPageNo(),resourceParams.getPageSize());
        }
        List<ResourceDto> resourceDtos = resources.stream().map(resource ->
                CopierUtil.copyProperties(resource,new ResourceDto())).collect(Collectors.toList());
        return new Page<>(resourceParams.getPageNo(),resourceParams.getPageSize(),resourcePageInfo.getTotal(),resourceDtos);
    }

    /**
     * 新增大章
     */
    public void save(Resource resource) {
        resourceMapper.insert(resource);
    }

    /**
     * 更新大章
     */
    public void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }
}
