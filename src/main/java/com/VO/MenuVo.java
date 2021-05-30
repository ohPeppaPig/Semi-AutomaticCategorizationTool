package com.VO;

import com.entity.CategoryLink;
import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
    // 一级分类
//    CategoryLink categoryOne;

    int sid;

    String sname;

//    int pid;

    // 二级分类
    List<CategoryLink> categoryTwo;
}
