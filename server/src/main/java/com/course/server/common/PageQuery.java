package com.course.server.common;


import com.alibaba.fastjson.JSON;


import java.io.Serializable;

/**
 * @author Gam saan
 * @version 1.0.0
 * @date 2020/6/14 10:43
 */
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 3539652731459672699L;
    /**
     * 分页
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;


    /**
     * 获取 分页
     *
     * @return pageNo 分页
     */
    public Integer getPageNo() {
        return this.pageNo;
    }

    /**
     * 设置 分页
     *
     * @param pageNo 分页
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取 每页数量
     *
     * @return pageSize 每页数量
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置 每页数量
     *
     * @param pageSize 每页数量
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}