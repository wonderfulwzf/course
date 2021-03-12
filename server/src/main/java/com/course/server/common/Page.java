package com.course.server.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T extends Serializable> {

    /**
     * 当前返回的数据是第几页的数据
     */
    private long currentPage;

    /**
     * 数据库中根据前端传递过来的分页大小计算出总共有多少页(非必须)
     */
    private long totalPage;

    /**
     * 数据库中总共有多少条数据(非必须)
     */
    private long totalRecord;

    /**
     * 返回的数据集合
     */
    private List<T> records = new ArrayList<>();

    /**
     * 当前的分页大小(非必须)
     */
    private long pageSize;

    public Page(long currentPage, long pageSize, long totalRecord, List<T> records) {
        this.totalPage = ((totalRecord + pageSize - 1) / pageSize);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.records = records;
    }

    public Page(long currentPage, long pageSize) {
        this.totalPage = 0;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = 0;
        this.records = null;
    }


    public Page() {
        super();
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
