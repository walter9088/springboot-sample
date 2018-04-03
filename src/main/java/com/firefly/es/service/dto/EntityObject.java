package com.firefly.es.service.dto;

import java.util.Date;

/**
 * @author walter
 */
public class EntityObject {


    /**
     * 省份
     */
    private String province;

    /**
     * 部门
     */
    private String department;

    /**
     * 标题
     */
    private String title;

    /**
     * link
     */
    private String link;


    /**
     * context
     */
    private String context;


    /**
     * date
     */

    private Date date;

    /**
     * 搜索关键词
     */
    private String keyword;

    /**
     * 查询起启地址
     */
    private int start = 0;

    /**
     * 查询结整地址
     */
    private int end = 20;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
