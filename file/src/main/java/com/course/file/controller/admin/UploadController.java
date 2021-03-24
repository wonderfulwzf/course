package com.course.file.controller.admin;


import com.course.server.common.Rest;
import com.course.server.domain.Test;
import com.course.server.dto.FileDto;
import com.course.server.service.FileService;
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

    public static final String BUSINESS_NAME = "文件";
    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private TestService testService;

    @Autowired
    private FileService fileService;

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
    public Rest<FileDto> upload(@RequestParam MultipartFile file,@RequestParam String use) throws IOException {
        Rest<FileDto> rest  = new Rest<>();


        String dir = use.toLowerCase();
        File fullDir = new File(FILE_PATH+dir);
        if(!fullDir.exists()){
            fullDir.mkdir();
        }


        LOG.info("上传文件开所{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //文件保存到本地
        String fileName = file.getOriginalFilename();
        String key  = UuidUtil.getShortUuid();

        //文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        //相对路径
        String path =dir+File.separator+ key+"."+suffix;

        String fullPath = FILE_PATH+path;
        File dest = new File(fullPath);
        file.transferTo(dest);

        //保存文件记录
        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setId(UuidUtil.getShortUuid());
        fileDto.setPath(path);
        fileDto.setName(fileName);
        fileDto.setUse(use);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);

        fileService.save(fileDto);

        //返回文件路径
        fileDto.setPath(FILE_DOMAIN+path);
        //rest.setData(FILE_DOMAIN+path);

        return rest.resultSuccessInfo(fileDto);
    }

    @RequestMapping("/test")
    public List<Test> list(){
        return testService.list();
    }
}
