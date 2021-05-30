package com.entity;

import lombok.Data;

@Data
public class RemarkedDoc {
    private Integer id;

    private String title;

    private Integer categoryLevelOneId ;

    private Integer categoryLevelTwoId;

    private String comment;
}
