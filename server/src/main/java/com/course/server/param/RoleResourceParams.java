package com.course.server.param;

import java.io.Serializable;

public class RoleResourceParams implements Serializable {

    private static final long serialVersionUID = 6966657241495672629L;

    private String id;

    private String roleId;

    private String resourceId;

    private long pageNo;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoleResourceParams{");
        sb.append("id='").append(id).append('\'');
        sb.append(", roleId='").append(roleId).append('\'');
        sb.append(", resourceId='").append(resourceId).append('\'');
        sb.append(", pageNo=").append(pageNo);
        sb.append(", pageSize=").append(pageSize);
        sb.append('}');
        return sb.toString();
    }
}