package com.entity;

import lombok.Data;

@Data
public class DocumentCategory {
    private Integer id;

    private String title;

    private String content;

    private Integer categoryLevelOneId ;

    private Integer categoryLevelTwoId;

    private String url;

    private String comment;
}
