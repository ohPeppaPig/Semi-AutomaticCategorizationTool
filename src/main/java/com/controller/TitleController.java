package com.controller;

import com.IRTools.util.FileIOUtil;
import com.VO.KeyWordNumsVo;
import com.VO.MenuVo;
import com.VO.TitleNums;
import com.dao.DocumentDao;
import com.entity.TFIDFWords;
import com.service.DocumentService;
import com.service.TitleClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/title")
public class TitleController {

    @Autowired
    DocumentService documentService;

    @Autowired
    TitleClusterService titleClusterService;

    @Autowired
    DocumentDao documentDao;

    /**
     * 展示出可能的分类供用户选择
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/categoryOne/{projectId}",method= RequestMethod.GET)
    public KeyWordNumsVo displayCategory(@PathVariable("projectId") int id) throws IOException {
       return titleClusterService.displayCategory(id);
        // TODO: 2021/4/13 文档集标题正则提取，用户确定关键词，扫描关键字标题，建立分类model，分类器分类

    }

    @RequestMapping(value="/categoryOne2/{projectId}",method= RequestMethod.GET)
    public TFIDFWords displayCategory2(@PathVariable("projectId") int id) throws IOException {
        String title = "E:\\gitt\\springboot_mybatis\\src\\main\\resources\\simu\\title.txt";
        TFIDFWords words = new TFIDFWords();
        List<String> res = new ArrayList<>();
        try {
            FileReader fr = new FileReader(title);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                res.add(str.split(":")[0]);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> high = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            high.add(res.get(i));
        }

        List<String> mid = new ArrayList<>();
        for (int i = 20; i < 40; i++) {
            mid.add(res.get(i));
        }

        List<String> low = new ArrayList<>();
        for (int i = 40; i < 60; i++) {
            low.add(res.get(i));
        }
        words.setHigh(high);
        words.setMid(mid);
        words.setLow(low);
        return words;

    }

    public static void main(String[] args) {
        String title = "E:\\gitt\\springboot_mybatis\\src\\main\\resources\\simu\\title2";
        List<String> res = new ArrayList<>();
        try {
            FileReader fr = new FileReader(title);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                res.add(str.split(":")[0]);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("res:"+res.size());
        List<String> high = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            high.add(res.get(i));
        }

        List<String> mid = new ArrayList<>();
        for (int i = 20; i < 40; i++) {
            mid.add(res.get(i));
        }

        List<String> low = new ArrayList<>();
        for (int i = 40; i < 60; i++) {
            low.add(res.get(i));
        }
        TFIDFWords words = new TFIDFWords();

        words.setHigh(high);
        words.setMid(mid);
        words.setLow(low);
        System.out.println(words);
    }



    /**
     * 展示菜单信息
     * @return
     */
    @RequestMapping(value="/menus",method= RequestMethod.GET)
    public List<MenuVo> menus(){

        return titleClusterService.findAllMenu();

    }

    /**
     * 展示菜单信息
     * @return
     */
    @RequestMapping(value="/menus/{projectId}",method= RequestMethod.GET)
    public List<MenuVo> menus2(@PathVariable("projectId") int id){

        // id = 0 思目数据
        // id = 1 政府数据
        return titleClusterService.findAllMenu(id);

    }

    /**
     * 查看title数量
     * @param name
     * @return
     */
    @RequestMapping(value="/titleNum/{name}",method= RequestMethod.GET)
    public int findTitleNums(@PathVariable("name") String name){
        return titleClusterService.findTitleNums(name);
    }


    /**
     * 合并一级分类簇
     * @param sid1
     * @param sid2
     */
    @RequestMapping(value="/mergeOneTitles/{sid1}/{sid2}",method= RequestMethod.POST)
    public void mergeOneTitleCluster(@PathVariable("sid1") Integer sid1,
                                     @PathVariable("sid2") Integer sid2){

        titleClusterService.mergeOneTitleCluster(sid1,sid2);
    }

    /**
     * 合并二级分类簇
     * @param sid1
     * @param sid2
     */
    @RequestMapping(value="/mergeTwoTitles/{sid1}/{sid2}",method= RequestMethod.POST)
    public String mergeTwoTitleCluster(@PathVariable("sid1") Integer sid1,
                                     @PathVariable("sid2") Integer sid2){

        return titleClusterService.mergeTwoTitleCluster(sid1, sid2);
    }

    @RequestMapping(value="/updateTitleName/{id}/{name}",method= RequestMethod.POST)
    public void updateTitleName(@PathVariable("id")int id,@PathVariable("name")String name){
        titleClusterService.updateTitleName(id,name);
    }




}
