package com.course.file.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.course.file.controller.util.VodUtil;
import com.course.server.common.Rest;
import com.course.server.dto.FileDto;
import com.course.server.service.FileService;
import com.course.server.utils.Base64ToMultipartFile;
import com.course.server.utils.UuidUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class VodController {
    private static final Logger LOG = LoggerFactory.getLogger(VodController.class);
    public static final String BUSINESS_NAME = "文件";
    @Value("${vod.accessKeyId}")
    private String accessKeyId;

    @Value("${vod.accessKeySecret}")
    private String accessKeySecret;
    @Autowired
    private FileService fileService;

    @RequestMapping("/vod")
    public Rest<FileDto> appendUpload(@RequestBody FileDto fileDto) throws IOException {
        Rest<FileDto> rest = new Rest<>();
        //上传对象内容
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        Integer shardIndex = fileDto.getShardIndex();
        //base64内容  转化为文件类型
        String shardbase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardbase64);
        String dir = use.toLowerCase();
        //返回播放路径
        String path = new StringBuilder().append(dir).append("/").append(key).append(".").append(suffix).toString();


        //文件上传

        String vod = "";
        String fileUrl = "";
        try {
            // 初始化VOD客户端并获取上传地址和凭证
            System.out.println(accessKeyId);
            System.out.println(accessKeySecret);
            DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
            CreateUploadVideoResponse createUploadVideoResponse = VodUtil.createUploadVideo(vodClient, path);
            // 执行成功会返回VideoId、UploadAddress和UploadAuth
            vod = createUploadVideoResponse.getVideoId();
            JSONObject uploadAuth = JSONObject.parseObject(
                    Base64.decodeBase64(createUploadVideoResponse.getUploadAuth()), JSONObject.class);
            JSONObject uploadAddress = JSONObject.parseObject(
                    Base64.decodeBase64(createUploadVideoResponse.getUploadAddress()), JSONObject.class);
            // 使用UploadAuth和UploadAddress初始化OSS客户端
            OSSClient ossClient = VodUtil.initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
            VodUtil.uploadLocalFile(ossClient, uploadAddress, shard.getInputStream());
            LOG.info("上传视频成功, vod : " + vod);
            GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, vod);
            System.out.println("获取视频信息, response : " + JSON.toJSONString(response));
            fileUrl = response.getMezzanine().getFileURL();

            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            LOG.info("上传视频失败, ErrorMessage : " + e.getLocalizedMessage(), e);
        }

        FileDto fileDo = fileService.selectByKey(key);
        fileDto.setPath(fileUrl);
        if(fileDo == null){
            //保存文件记录
            LOG.info("保存文件记录开始");
            fileDto.setId(UuidUtil.getShortUuid());
            fileDto.setVod(vod);
            fileDto.setPath(fileUrl);
            fileService.save(fileDto);
        }
        else {
            //保存文件记录
            LOG.info("更新文件记录开始");
            fileDo.setShardIndex(shardIndex);
            fileService.update(fileDo);
        }
        System.out.println(fileDto);
        return rest.resultSuccessInfo(fileDto);
    }


    @RequestMapping(value = "/get-auth/{vod}", method = RequestMethod.GET)
    public Rest<String> getAuth(@PathVariable String vod) throws ClientException {
        LOG.info("获取播放授权开始: ");
        Rest<String> rest = new Rest<>();
        DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = VodUtil.getVideoPlayAuth(client, vod);
            LOG.info("授权码 = {}", response.getPlayAuth());
            rest.setData(response.getPlayAuth());
            //VideoMeta信息
            LOG.info("VideoMeta = {}", JSON.toJSONString(response.getVideoMeta()));
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        LOG.info("获取播放授权结束");
        return rest;
    }
}
