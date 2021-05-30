package com.VO;

import lombok.Data;

import java.util.List;

@Data
public class DocumentClusterDiffVo {
    List<SecondaryMenuVo> secondaryMenuVoList;

    List<SecondaryMenuVo> diffPre;

    List<SecondaryMenuVo> diffAfter;



}
