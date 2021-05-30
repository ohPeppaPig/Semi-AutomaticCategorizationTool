package com.service;

import com.VO.KeyWordNumsVo;
import com.VO.MenuVo;
import com.VO.SecondaryMenuVo;
import com.VO.TitleNums;
import com.dao.DocumentDao;
import com.dao.TitleClusterDao;
import com.entity.CategoryLink;
import com.entity.Document;
import com.entity.DocumentCategory;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class TitleClusterService {

    @Autowired
    TitleClusterDao titleClusterDao;

    @Autowired
    DocumentDao documentDao;

    @Autowired
    AlgorithmService algorithmService;


    /**
     * 确定一级标题，存入数据库
     * @param titles
     */
    public void insertCategoryLevel(List<String> titles){
        for (String title:
             titles) {
            titleClusterDao.insertCategoryLevel(title,0);
        }
    }


    /**
     * 展示菜单信息
     * @return
     */
    public List<MenuVo> findAllMenu(){

        List<MenuVo> menu = new ArrayList<>();
        // 找出一级分类
        List<CategoryLink> categoryOnes = titleClusterDao.findCategoryByPid(0);

        // 找出一级分类下的二级分类

        for (CategoryLink categoryOne:
                categoryOnes) {
            MenuVo menuVo = new MenuVo();
            List<CategoryLink> categoryTwos = titleClusterDao.findCategoryByPid(categoryOne.getSid());
//            menuVo.setCategoryOne(categoryOne);
            menuVo.setSid(categoryOne.getSid());
            menuVo.setSname(categoryOne.getSname());
            menuVo.setCategoryTwo(categoryTwos);
            menu.add(menuVo);
        }
        return menu;

    }

    /**
     * 展示菜单信息
     * @return
     */
    public List<MenuVo> findAllMenu(int id){

        List<MenuVo> menu = new ArrayList<>();
        // 找出一级分类
        List<CategoryLink> categoryOnes = titleClusterDao.findCategoryByPid(id);

        // 找出一级分类下的二级分类

        for (CategoryLink categoryOne:
                categoryOnes) {
            MenuVo menuVo = new MenuVo();
            List<CategoryLink> categoryTwos = titleClusterDao.findCategoryByPid(categoryOne.getSid());
//            menuVo.setCategoryOne(categoryOne);
            menuVo.setSid(categoryOne.getSid());
            menuVo.setSname(categoryOne.getSname());
            menuVo.setCategoryTwo(categoryTwos);
            menu.add(menuVo);
        }
        return menu;

    }
    /**
     * 展示 二级菜单的名称 与 相关信息
     * @param sid
     * @return
     */
    public List<SecondaryMenuVo>  secondaryMenu(int sid){

        List<SecondaryMenuVo> res = new ArrayList<>();
        // 根据一级标题id 查询出二级标题的集合
        List<CategoryLink> twoId = titleClusterDao.findCategoryByPid(sid);
        //遍历二级分类集合，找出二级分类的关键句描述
        for (CategoryLink link:
             twoId) {
            SecondaryMenuVo secondaryMenuVo = new SecondaryMenuVo();
            List<DocumentCategory> docs = documentDao.findDocumentsByTwoId(link.getSid());
            String keySentence = algorithmService.extractkeySentence(docs);
            secondaryMenuVo.setCategoryTwo(link);
            secondaryMenuVo.setDescription(keySentence);
            res.add(secondaryMenuVo);
        }
        return res;
    }

    /**
     * 展示思目的可能一级标题
     * @return
     */
    public KeyWordNumsVo displayCategory(int id) throws IOException {
        KeyWordNumsVo keyWordNumsVo = new KeyWordNumsVo();
        if(id == 0){
            List<String> key = new ArrayList<>();
            key.add("衣食住行");
            key.add("成长伙伴");
            key.add("产学研教");
            key.add("超级账号");
            key.add("物资库");
            key.add("盟客网");
            key.add("产品会议");
            key.add("智慧城市");
            List<String> other = new ArrayList<>();
            other.add("旅游申请");
            other.add("会议纪要");
            other.add("思目论坛");
            other.add("需求更新");
            other.add("资金");
            other.add("招聘");
            other.add("保修");
            other.add("交易");
            keyWordNumsVo.setKeyWords(key);
            keyWordNumsVo.setOtherWords(other);
        }else if (id == 1){
            List<String> key = new ArrayList<>();
            key.add("科技厅");
            key.add("财政厅");
            key.add("工信厅");
            List<String> other = new ArrayList<>();
            other.add("科技厅/财政厅");
            other.add("财政厅/发改委");
            other.add("财政厅/环境厅");
            other.add("财政厅/农业厅");
            keyWordNumsVo.setKeyWords(key);
            keyWordNumsVo.setOtherWords(other);
        }


        return keyWordNumsVo;

    }

    /**
     * 查找标题关键字的数量
     * @param name
     * @return
     */
    public int findTitleNums(String name){
        return documentDao.findTitleNums(name);
    }

    /**
     * 合并一级分类簇
     * @param id1
     * @param id2
     */
    public void mergeOneTitleCluster(int id1,int id2){
        // id2分类下的二级分类pid 修改为id1

        //再将id2的 sid 改为 id1
    }


    /**
     * 合并二级分类簇
     * @param id1
     * @param id2
     */
    public String mergeTwoTitleCluster(int id1,int id2){
        // 将id2 直接改为 id1

        //再次提取合并分类簇的标题
        return "";
    }




    public static void main(String[] args) throws IOException {
        TitleClusterService t = new TitleClusterService();
        System.out.println(t.displayCategory(1));
    }

    public void updateTitleName(int id,String name){
        titleClusterDao.updateTitleName(id,name);
    }



}
