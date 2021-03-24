package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.File;
import com.course.server.domain.FileExample;
import com.course.server.dto.FileDto;
import com.course.server.mapper.FileMapper;
import com.course.server.param.FileParams;
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
public class FileService {

    @Autowired
    private FileMapper fileMapper;


    /**
     * 返回列表
     */
    public Page<FileDto> list(FileParams fileParams) {

        PageHelper.startPage((int)fileParams.getPageNo(),(int)fileParams.getPageSize());
        //查询参数
        FileExample fileExample = new FileExample();
        List<File> files = fileMapper.selectByExample(fileExample);
        PageInfo<File> filePageInfo = new PageInfo<>(files);

        if(files==null){
            return new Page<>(fileParams.getPageNo(),fileParams.getPageSize());
        }
        List<FileDto> fileDtos = files.stream().map(file ->
                CopierUtil.copyProperties(file,new FileDto())).collect(Collectors.toList());
        return new Page<>(fileParams.getPageNo(),fileParams.getPageSize(),filePageInfo.getTotal(),fileDtos);
    }

    /**
     * 新增大章
     */
    public void save(FileDto fileDto) {
        if(fileDto != null){
            fileMapper.insert( CopierUtil.copyProperties(fileDto,new File()));
        }
    }

    /**
     * 更新大章
     */
    public void update(File file) {
        fileMapper.updateByPrimaryKey(file);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }
}
