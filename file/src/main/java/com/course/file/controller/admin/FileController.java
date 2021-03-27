package com.course.file.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.dto.FileDto;
import com.course.server.param.FileParams;
import com.course.server.service.FileService;
import com.course.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王智芳
 * @data 2021/3/6 13:38
 */
@RestController
@RequestMapping("/admin/file")
public class FileController {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    public static final String BUSINESS_NAME = "文件";

    @Autowired
    private FileService fileService;

    /**
    * @description: 查询大章列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<FileDto>> fileList(@RequestBody FileParams page){
        Rest<Page<FileDto>> rest = new Rest<>();
        Page<FileDto> list = fileService.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody FileDto fileDto){
        Rest rest = new Rest();
        fileDto.setId(UuidUtil.getShortUuid());
        fileService.save(fileDto);
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody FileDto fileDto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(fileDto.getId())){
            return rest.resultSuccess("添加大章失败");
        }
        fileService.update(fileDto);
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        fileService.delete(id);
        return rest.resultSuccess("删除大章成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
