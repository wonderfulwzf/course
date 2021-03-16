package com.course.server.param;

/**
 * @author 王智芳
 * @data 2021/3/13 13:20
 */
public class ChapterParams  {

    private String id;

    private String courseId;

    private String name;
    /**
     * 当前返回的数据是第几页的数据
     */
    private long pageNo;
    /**
     * 当前的分页大小(非必须)
     */
    private long pageSize;

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
