package com.VO;

import lombok.Data;

/**
 * 展示一级title的名称与数量
 */
@Data
public class TitleNums {

    String name;

    Integer nums;

    public TitleNums(String name, Integer nums) {
        this.name = name;
        this.nums = nums;
    }

    public TitleNums() {
    }
}
