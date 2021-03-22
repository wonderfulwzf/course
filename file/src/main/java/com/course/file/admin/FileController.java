package com.course.file.admin;


import com.course.server.common.Rest;
import com.course.server.domain.Test;
import com.course.server.service.TestService;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private TestService testService;

    /**
     * 文件上传
     * @param file
     */
    @RequestMapping("/upload")
    public Rest upload(@RequestParam MultipartFile file) throws IOException {
        Rest rest  = new Rest();
        LOG.info("上传文件开所{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //文件保存到本地
        String fileName = file.getOriginalFilename();
        String key  = UuidUtil.getShortUuid();
        String fullPath = "C:/img/"+key+"-"+fileName;
        File dest = new File(fullPath);
        file.transferTo(dest);

        return rest.resultSuccess("ok");
    }

    @RequestMapping("/test")
    public List<Test> list(){
        return testService.list();
    }
}
