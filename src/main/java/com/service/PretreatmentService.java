package com.service;

import com.VO.DocSegementVO;
import com.dao.PretreatmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PretreatmentService {

    @Autowired
    PretreatmentDao pretreatmentDao;

    public void uploadReservedWords(String[] strs){
        for (String str:
             strs) {
            pretreatmentDao.insertReversedWords(str);
        }

    }

    public List<DocSegementVO> findSegementWords(){
        List<DocSegementVO> res = new ArrayList<>();
        DocSegementVO docSegementVO = new DocSegementVO();
        //查看所有切词
        res.add(docSegementVO);
        return res;
    }
}
