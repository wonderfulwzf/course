package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.${Domain};
import com.course.server.domain.${Domain}Example;
import com.course.server.dto.${Domain}Dto;
import com.course.server.mapper.${Domain}Mapper;
import com.course.server.param.${Domain}Params;
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
public class ${Domain}Service {

    @Autowired
    private ${Domain}Mapper ${domain}Mapper;


    /**
     * 返回列表
     */
    public Page<${Domain}Dto> list(${Domain}Params ${domain}Params) {

        PageHelper.startPage((int)${domain}Params.getPageNo(),(int)${domain}Params.getPageSize());
        //查询参数
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> ${domain}PageInfo = new PageInfo<>(${domain}s);

        if(${domain}s==null){
            return new Page<>(${domain}Params.getPageNo(),${domain}Params.getPageSize());
        }
        List<${Domain}Dto> ${domain}Dtos = ${domain}s.stream().map(${domain} ->
                CopierUtil.copyProperties(${domain},new ${Domain}Dto())).collect(Collectors.toList());
        return new Page<>(${domain}Params.getPageNo(),${domain}Params.getPageSize(),${domain}PageInfo.getTotal(),${domain}Dtos);
    }

    /**
     * 新增大章
     */
    public void save(${Domain} ${domain}) {
        ${domain}Mapper.insert(${domain});
    }

    /**
     * 更新大章
     */
    public void update(${Domain} ${domain}) {
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
