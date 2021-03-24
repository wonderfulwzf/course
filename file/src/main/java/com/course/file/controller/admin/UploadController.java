package com.course.file.controller.admin;


import com.course.server.common.Rest;
import com.course.server.domain.Test;
import com.course.server.service.TestService;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
@RequestMapping("/admin")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private TestService testService;

    /**
     * 文件路径
     */
    @Value("${file.path}")
    private String FILE_PATH;

    /**
     * 文件路径
     */
    @Value("${file.domain}")
    private String FILE_DOMAIN;

    /**
     * 文件上传
     * @param file
     */
    @RequestMapping("/upload")
    public Rest upload(@RequestParam MultipartFile file) throws IOException {
        Rest rest  = new Rest();
        System.out.println(FILE_DOMAIN);
        LOG.info("上传文件开所{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //文件保存到本地
        String fileName = file.getOriginalFilename();
        String key  = UuidUtil.getShortUuid();
        String fullPath = FILE_PATH+key+"-"+fileName;
        File dest = new File(fullPath);
        file.transferTo(dest);

        //返回文件路径
        rest.setData(FILE_DOMAIN+"/f/"+key+"-"+fileName);

        return rest;
    }

    @RequestMapping("/test")
    public List<Test> list(){
        return testService.list();
    }
}
