package com.course.file.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetMezzanineInfoResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /*可指定分片上传时每个分片的大小，默认为1M字节*/
        request.setPartSize(10 * 1024 * 1024L);
        /*可指定分片上传时的并发线程数，默认为1（注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /*是否开启断点续传，默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        /*OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位: 毫秒，默认为300000毫秒*/
        //request.setSlowRequestsThreshold(300000L);
        /*可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
        //request.setSlowRequestsThreshold(300000L);
        /*是否使用默认水印（可选），指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setIsShowWaterMark(true);
        /*自定义消息回调设置（可选），参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData*/
        // request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}");

        /*视频分类ID（可选）*/
        request.setCateId(1000285747L);
        /*视频标签,多个用逗号分隔（可选）*/
        //request.setTags（"标签1,标签2"）;
        /*视频描述（可选）*/
        //request.setDescription("视频描述");
        /*封面图片（可选）*/
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /*模板组ID（可选）*/
        request.setTemplateGroupId("8b686ca6d2b4e35abca7da47ee35088a");
        /*点播服务接入点*/
        //request.setApiRegionId("cn-shanghai");
        /*ECS部署区域，如果与点播存储（OSS）区域相同，则自动使用内网上传文件至存储*/
        // request.setEcsRegionId("cn-shanghai");
        /*存储区域（可选）*/
        //request.setStorageLocation("in-2017032*****18266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /*开启默认上传进度回调*/
        // request.setPrintProgress(true);
        /*设置自定义上传进度回调（必须继承 ProgressListener）*/
        // request.setProgressListener(new PutObjectProgressListener());
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("请求id="+response.getRequestId());  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("视频id"+response.getVideoId());
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("视频id="+response.getVideoId());
            System.out.print("错误码="+response.getCode());
            System.out.print("错误信息="+response.getMessage());
        }
    }

    private GetMezzanineInfoResponse getVodMessage(){
        //获取视频信息
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        GetMezzanineInfoResponse response = new GetMezzanineInfoResponse();
        try {
            GetMezzanineInfoRequest request = new GetMezzanineInfoRequest();
            request.setVideoId("VideoId");
            //源片下载地址过期时间
            request.setAuthTimeout(3600L);
            response = client.getAcsResponse(request);
            System.out.print("Mezzanine.VideoId = " + response.getMezzanine().getVideoId() + "\n");
            System.out.print("Mezzanine.FileURL = " + response.getMezzanine().getFileURL() + "\n");
            System.out.print("Mezzanine.Width = " + response.getMezzanine().getWidth() + "\n");
            System.out.print("Mezzanine.Height = " + response.getMezzanine().getHeight() + "\n");
            System.out.print("Mezzanine.Size = " + response.getMezzanine().getSize() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
        return response;
    }
}
