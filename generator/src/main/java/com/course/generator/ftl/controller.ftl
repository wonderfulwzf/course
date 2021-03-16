package com.course.${module}.controller.admin;


import com.course.server.common.Page;
import com.course.server.common.Rest;
import com.course.server.domain.${Domain};
import com.course.server.dto.${Domain}Dto;
import com.course.server.param.${Domain}Params;
import com.course.server.service.${Domain}Service;
import com.course.server.utils.CopierUtil;
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
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {
    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);

    public static final String BUSINESS_NAME = "${tableNameCn}";

    @Autowired
    private ${Domain}Service ${domain}Service;

    /**
    * @description: 查询大章列表
    * @author wangzhifang
    * @createTime：2021/3/12 20:28
    */
    @RequestMapping("/list")
    public Rest<Page<${Domain}Dto>> ${domain}List(@RequestBody ${Domain}Params page){
        Rest<Page<${Domain}Dto>> rest = new Rest<>();
        Page<${Domain}Dto> list = ${domain}Service.list(page);
        if(list==null||list.getRecords()==null){
            return rest.resultSuccess("数据为空",new Page<>(page.getPageNo(),page.getPageSize()));
        }
        return rest.resultSuccessInfo(new Page<>(page.getPageSize(),page.getPageSize(),list.getTotalRecord(),list.getRecords()));
    }

    @RequestMapping("/save")
    public Rest save(@RequestBody ${Domain}Dto ${domain}Dto){
        Rest rest = new Rest();
        ${domain}Dto.setId(UuidUtil.getShortUuid());
        ${Domain} ${domain} = new ${Domain}();
        ${domain}Service.save(CopierUtil.copyProperties(${domain}Dto,${domain}));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/update")
    public Rest update(@RequestBody ${Domain}Dto ${domain}Dto){
        Rest rest = new Rest();
        if(!StringUtils.hasText(${domain}Dto.getId())){
            return rest.resultSuccess("添加大章失败");
        }
        ${Domain} ${domain} = new ${Domain}();
        ${domain}Service.update(CopierUtil.copyProperties(${domain}Dto,${domain}));
        return rest.resultSuccess("添加大章成功");
    }

    @RequestMapping("/delete/{id}")
    public Rest delete(@PathVariable String id){
        Rest rest = new Rest();
        ${domain}Service.delete(id);
        return rest.resultSuccess("删除大章成功");
    }


    @RequestMapping("/test")
    public String test(){
        return "321";
    }
}
