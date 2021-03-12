package com.course.server.service;


import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.utils.CopierUtil;
import com.github.pagehelper.PageHelper;
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
    public List<ChapterDto> list() {
        PageHelper.startPage(1,1);
        List<Chapter> chapters = chapterMapper.selectByExample(null);
        //stream
        return chapters.stream().map(chapter -> {
            ChapterDto chapterDto = new ChapterDto();
            return CopierUtil.copyProperties(chapter,chapterDto);
        }).collect(Collectors.toList());
    }
}
