package com.service;

import com.VO.AdminStatisticsVO;
import com.dao.DocumentDao;
import com.dao.TitleClusterDao;
import com.entity.AdminChartDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    TitleClusterDao titleClusterDao;
    @Autowired
    DocumentDao documentDao;

    public AdminStatisticsVO statistics(int projectId){
        AdminStatisticsVO statisticsVO = new AdminStatisticsVO();
        if(projectId == 0){
            int commentNums = documentDao.findCommentNums();
            statisticsVO.setCommentNums(commentNums);
            statisticsVO.setAllDocNums(1027);
            statisticsVO.setOneCategory(13);
            statisticsVO.setTwoCategory(70);
        }else {
            int commentNums = documentDao.findCommentNums2();
            statisticsVO.setCommentNums(20);
            statisticsVO.setAllDocNums(50);
            statisticsVO.setOneCategory(3);
            statisticsVO.setTwoCategory(7);
        }

        return statisticsVO;
    }

    public List<AdminChartDisplay> AdminChartDisplay(int id){
            return titleClusterDao.findChartNums(id);
    }
}
