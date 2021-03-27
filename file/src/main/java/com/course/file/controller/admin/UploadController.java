package com.course.file.controller.admin;


import com.course.server.common.Rest;
import com.course.server.domain.Test;
import com.course.server.dto.FileDto;
import com.course.server.service.FileService;
import com.course.server.service.TestService;
import com.course.server.utils.Base64ToMultipartFile;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
     */
    @RequestMapping("/upload")
    public Rest<FileDto> upload(@RequestBody FileDto fileDto) throws IOException {
        Rest<FileDto> rest  = new Rest<>();

        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        Integer shardIndex = fileDto.getShardIndex();
        //base64内容  转化为文件类型
        String shardbase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardbase64);


        String dir = use.toLowerCase();
        File fullDir = new File(FILE_PATH+dir);
        if(!fullDir.exists()){
            fullDir.mkdir();
        }

        //相对路径
        String path = String.format("%s%s%s.%s.%d", dir, File.separator, key, suffix, shardIndex);

        String fullPath = FILE_PATH+path;
        File dest = new File(fullPath);
        shard.transferTo(dest);

        //保存文件记录
        LOG.info("保存文件记录开始");

        fileDto.setId(UuidUtil.getShortUuid());
        fileDto.setPath(path);

        FileDto fileDo = fileService.selectByKey(key);
        if(fileDo == null){
            fileService.save(fileDto);
        }
        else {
            fileDo.setShardIndex(shardIndex);
            fileService.update(fileDo);
        }
        //返回文件路径
        fileDto.setPath(FILE_DOMAIN+path);
        //rest.setData(FILE_DOMAIN+path);

        return rest.resultSuccessInfo(fileDto);
    }

    @GetMapping("/merge")
    public Rest merge() throws FileNotFoundException {
        Rest rest = new Rest();
        File newFile = new File(FILE_PATH+"课程/test.mp4");
        FileOutputStream outputStream = new FileOutputStream(newFile,true);
        FileInputStream fileInputStream = null;
        byte[] byt = new byte[20 * 1024 * 1024];
        int len;
        try{
            fileInputStream = new FileInputStream(new File(FILE_PATH + "课程/gSTmRf7d.blob"));
            while ((len = fileInputStream.read(byt))!=-1){
                outputStream.write(byt,0,len);
            }
            fileInputStream = new FileInputStream(new File(FILE_PATH + "课程/BLh3vf6g.blob"));
            while ((len = fileInputStream.read(byt))!=-1){
                outputStream.write(byt,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (IOException e) {
                LOG.info("IO流关闭异常");
            }
        }
        return rest;
    }

    @RequestMapping("/test")
    public List<Test> list(){
        return testService.list();
    }
}
