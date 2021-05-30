package com.controller;

import com.VO.RemarkedDocumentVo;
import com.entity.Document;
import com.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    DocumentService documentService;

    @RequestMapping(value="/insertComment/{id}/{comment}",method= RequestMethod.POST)
    public void insertComment(@PathVariable("id")Integer id, @PathVariable("comment")String comment){
        documentService.updateDocumentComment(id,comment);
    }

    @RequestMapping(value="/findComments/{ids}",method= RequestMethod.GET)
    public List<RemarkedDocumentVo> insertComment(@PathVariable("ids")List<Integer> ids){
        return documentService.findRemarkedDocsByIds(ids);
    }

}
