package com.course.file.controller.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.course.server.common.Rest;
import com.course.server.dto.FileDto;
import com.course.server.service.FileService;
import com.course.server.utils.Base64ToMultipartFile;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class OssController {
    private static final Logger LOG = LoggerFactory.getLogger(OssController.class);
    public static final String BUSINESS_NAME = "文件";
    //账号
    @Value("${accessKeyId}")
    private String accessKeyId;
    //密码
    @Value("${accessKeySecret}")
    private String accessKeySecret;
    //地域节点
    @Value("${endpoint}")
    private String endpoint;
    //外网路径
    @Value("${ossDomainhttp}")
    private String ossDomainhttp;
    //bucket名称
    @Value("${bucket}")
    private String bucket;
    @Autowired
    private FileService fileService;

    @RequestMapping("/append_upload")
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

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");



        // 通过AppendObjectRequest设置多个参数。  bucketName,path,对象，类型
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucket, path, new ByteArrayInputStream(shard.getBytes()),meta);

        // 通过AppendObjectRequest设置单个参数。
        // 设置Bucket名称。
        //appendObjectRequest.setBucketName("<yourBucketName>");
        // 设置Object名称。即不包含Bucket名称在内的Object的完整路径，例如example/test.txt。
        //appendObjectRequest.setKey("<yourObjectName>");
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为InputStream类型。
        //appendObjectRequest.setInputStream(new ByteArrayInputStream(content1.getBytes()));
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为File类型。
        //appendObjectRequest.setFile(new File("<yourLocalFile>"));
        // 指定文件的元信息，第一次追加时有效。
        //appendObjectRequest.setMetadata(meta);

        // 第一次追加。
        // 设置文件的追加位置。
        appendObjectRequest.setPosition((long) ((shardIndex -1)*fileDto.getShardSize()));
        AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
        // 文件的64位CRC值。此值根据ECMA-182标准计算得出。
        System.out.println(appendObjectResult.getObjectCRC());

        //// 第二次追加。
        //// nextPosition指明下一次请求中应当提供的Position，即文件当前的长度。
        //appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
        //appendObjectRequest.setInputStream(new ByteArrayInputStream(content2.getBytes()));
        //appendObjectResult = ossClient.appendObject(appendObjectRequest);
        //
        //// 第三次追加。
        //appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
        //appendObjectRequest.setInputStream(new ByteArrayInputStream(content3.getBytes()));
        //appendObjectResult = ossClient.appendObject(appendObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();

        FileDto fileDo = fileService.selectByKey(key);
        fileDto.setPath(ossDomainhttp+"/"+path);
        if(fileDo == null){
            //保存文件记录
            LOG.info("保存文件记录开始");
            fileDto.setId(UuidUtil.getShortUuid());
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

    public void simpleUpload(){

    }
}
