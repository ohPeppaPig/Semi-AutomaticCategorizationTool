package com.controller;

import com.VO.AdminStatisticsVO;
import com.entity.AdminChartDisplay;
import com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;


    /**
     * 返回 工作台展示数据
     * @param projectId
     * @return
     */
    @RequestMapping(value="/statistics/{projectId}",method= RequestMethod.GET)
    public AdminStatisticsVO statistics(@PathVariable("projectId") int projectId){
        return adminService.statistics(projectId);
    }


    @RequestMapping(value="/chartDisplay/{id}",method= RequestMethod.GET)
    public List<AdminChartDisplay> AdminChartDisplay(@PathVariable("id") int id){
        return adminService.AdminChartDisplay(id);
    }

}
