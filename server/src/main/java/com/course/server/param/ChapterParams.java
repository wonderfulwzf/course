package com.course.server.param;

import java.io.Serializable;

/**
 * @author 王智芳
 * @data 2021/3/13 13:20
 */
public class ChapterParams implements Serializable {

    private static final long serialVersionUID = 8114716282770806719L;
    /**
     * 当前返回的数据是第几页的数据
     */
    private long currentPage;
    /**
     * 当前的分页大小(非必须)
     */
    private long pageSize;

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
