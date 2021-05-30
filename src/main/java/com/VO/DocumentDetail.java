package com.VO;

import com.entity.DocumentSimple;
import lombok.Data;

import java.util.List;

@Data
public class DocumentDetail {

    Integer id;

    String title;

    String keySentence;

    String content;

    List<String> urls;

    List<DocumentSimple> similaryDocs;

}
