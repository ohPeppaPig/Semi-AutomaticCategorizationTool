package com.VO;

import lombok.Data;

import java.util.List;

@Data
public class DetailedDocumentVo {
    String title;
    // 关键句：取三句话
    String keySentence;
    // 文档详细内容
    String content;
    List<String> comments;
    // 图片url链接
    List<String> urls;
}
