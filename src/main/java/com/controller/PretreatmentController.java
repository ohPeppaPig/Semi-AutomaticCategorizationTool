package com.controller;

import com.VO.DocSegementVO;
import com.service.PretreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pre")
public class PretreatmentController {
    @Autowired
    PretreatmentService pretreatmentService;
    /**
     * 上传停用词
     * @param str
     */
    @RequestMapping(value="/uploadReservedWords/{str}",method= RequestMethod.POST )
    public void uploadReservedWords(@PathVariable("str") String str){
        // 按照逗号，分割开来
        String[] split = str.split(",");
        // 将string加载到停用词表中
        pretreatmentService.uploadReservedWords(split);
    }

    /**
     * 查看文档切词结果
     * @return
     */
    @RequestMapping(value="/findSegementWords",method= RequestMethod.GET )
    public List<DocSegementVO> findSegementWords(){
        return pretreatmentService.findSegementWords();
    }


}
