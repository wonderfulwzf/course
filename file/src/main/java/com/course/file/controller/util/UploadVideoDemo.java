package com.course.file.controller.util;


import com.aliyun.vod.upload.impl.UploadImageImpl;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadImageRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadImageResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import org.springframework.beans.factory.annotation.Value;

public class UploadVideoDemo {

    @Value("${vod.accessKeyId}")
    private static String accessKeyId;

    @Value(("${vod.accessKeySecret}"))
    private static String accessKeySecret;


    public static void main(String[] args) {
        //1.音视频上传-本地文件上传
        //视频标题(必选)  文件名
        String title = "测试视频上传";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "C:\\Users\\80504\\Desktop\\image\\p.mp4";
        //本地文件上传
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);

        //2.图片上传-本地文件上传
        //testUploadImageLocalFile(accessKeyId, accessKeySecret);
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

    /**
     * 图片上传接口，本地文件上传示例
     * 参数参考文档 https://help.aliyun.com/document_detail/55619.html
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    private static void testUploadImageLocalFile(String accessKeyId, String accessKeySecret) {
        //图片类型（必选）取值范围：default（默认），cover（封面），watermark（水印）
        String imageType = "cover";
        UploadImageRequest request = new UploadImageRequest(accessKeyId, accessKeySecret, imageType);
        /*图片文件扩展名（可选）取值范围：png，jpg，jpeg*/
        //request.setImageExt("png");
        /*图片标题（可选）长度不超过128个字节，UTF8编码*/
        //request.setTitle("图片标题");
        /*图片标签（可选）单个标签不超过32字节，最多不超过16个标签，多个用逗号分隔，UTF8编码*/
        //request.setTags("标签1,标签2");
        /*存储区域（可选）*/
        //request.setStorageLocation("out-4f3952f78c021*****013e7.oss-cn-shanghai.aliyuncs.com");
        /*流式上传时，InputStream为必选，fileName为源文件名称，如:文件名称.png（可选）*/
        //request.setFileName("测试文件名称.png");
        /*开启默认上传进度回调*/
        // request.setPrintProgress(true);
        /*设置自定义上传进度回调（必须继承 ProgressListener）*/
        // request.setProgressListener(new PutObjectProgressListener());
        /*点播服务接入点*/
        //request.setApiRegionId("cn-shanghai");
        /*ECS部署区域，如果与点播存储（OSS）区域相同，则自动使用内网上传文件至存储*/
        // request.setEcsRegionId("cn-shanghai");

        UploadImageImpl uploadImage = new UploadImageImpl();
        UploadImageResponse response = uploadImage.upload(request);
        System.out.print("RequestId="+response.getRequestId());
        if (response.isSuccess()) {
            System.out.print("ImageId="+response.getImageId());
            System.out.print("ImageURL="+response.getImageURL());
        } else {
            System.out.print("ErrorCode="+response.getCode());
            System.out.print("ErrorMessage="+response.getMessage());
        }


    }
}