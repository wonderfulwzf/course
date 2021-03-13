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
    public Page<Chapter> list(ChapterParams chapterParams) {
        Page<Chapter> page = new Page<>();
        PageHelper.startPage((int)chapterParams.getCurrentPage(),(int)chapterParams.getPageSize());
        //查询参数
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> chapterPageInfo = new PageInfo<>(chapters);
        page.setTotalRecord(chapterPageInfo.getTotal());
        //获得数据
        page.setRecords(chapters);
        return page;
    }

    /**
     * 新增大章
     */
    public void save(ChapterDto chapterDto) {
        Chapter chapter = new Chapter();
        CopierUtil.copyProperties(chapterDto,chapter);
        chapterMapper.insert(chapter);
    }
}
