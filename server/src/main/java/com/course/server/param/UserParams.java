package com.course.server.param;

import java.io.Serializable;

public class UserParams implements Serializable {

    private static final long serialVersionUID = -8995737374386433372L;
    private String id;

    private String loginName;

    private String name;

    private String password;

    private long pageNo;

    private long pageSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserParams{");
        sb.append("id='").append(id).append('\'');
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", pageNo=").append(pageNo);
        sb.append(", pageSize=").append(pageSize);
        sb.append('}');
        return sb.toString();
    }
}
