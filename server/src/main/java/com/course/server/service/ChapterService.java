package com.course.server.service;


import com.course.server.common.Page;
import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.param.ChapterParams;
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
public class ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;


    /**
     * 返回列表
     */
    public Page<ChapterDto> list(ChapterParams chapterParams) {

        PageHelper.startPage((int)chapterParams.getPageNo(),(int)chapterParams.getPageSize());
        //查询参数
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> chapterPageInfo = new PageInfo<>(chapters);

        if(chapters==null){
            return new Page<>(chapterParams.getPageNo(),chapterParams.getPageSize());
        }
        List<ChapterDto> chapterDtos = chapters.stream().map(chapter ->
                CopierUtil.copyProperties(chapter,new ChapterDto())).collect(Collectors.toList());
        return new Page<>(chapterParams.getPageNo(),chapterParams.getPageSize(),chapterPageInfo.getTotal(),chapterDtos);
    }

    /**
     * 新增大章
     */
    public void save(Chapter chapter) {
        chapterMapper.insert(chapter);
    }

    /**
     * 更新大章
     */
    public void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }
    /**
     * 删除大章
     */
    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}
