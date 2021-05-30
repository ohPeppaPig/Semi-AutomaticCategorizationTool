package com.entity;

import lombok.Data;

@Data
public class CategoryLink {
    private Integer sid;
    private String sname;
    private Integer pid;

    public CategoryLink(Integer sid, String sname, Integer pid) {
        this.sid = sid;
        this.sname = sname;
        this.pid = pid;
    }
}
