package com.wang.springboot_mybatis;

import com.IRTools.util.FileIOUtil;
import com.IRTools.util.SegementUtil;
import com.Utils.TF_IDF;
import com.dao.ClassifictionDao;
import com.dao.DocumentDao;
import com.dao.TitleClusterDao;
import com.entity.CategoryLink;
import com.entity.Document;
import com.entity.DocumentCategory;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.service.*;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SpringBootTest
public class govermentTest {

    @Autowired
    UserService service;

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentDao documentDao;

    @Autowired
    AlgorithmService algorithmService;

    @Autowired
    classifictionService classifictionService;

    @Autowired
    ClassifictionDao classifictionDao;

    @Autowired
    SimilarService similarService;

    @Autowired
    TitleClusterDao titleClusterDao;

    @Autowired
    TitleClusterService titleClusterService;

    @Test
    void meuns(){
        List<String> title = new ArrayList<>();
        title.add("工信厅");
        title.add("科技厅");
        title.add("财政厅");
        title.add("科技厅/财政厅");
        title.add("财政厅/发改委");
        title.add("财政厅/环境厅");
        title.add("财政厅/农业厅");
        for (String str:
             title) {
            titleClusterDao.insertCategoryLevel(str,1);
        }
    }

    @Test
    void firstClass(){
        String path = "E:\\gitt\\springboot_mybatis\\src\\main\\resources\\govement";
        File file = new File(path);
        int id = 1;
        for (File f:
             file.listFiles()) {

            String name = f.getName();
            for (File f1:
                 f.listFiles()) {
                int sid = titleClusterDao.findCategorySidBySname(name);
                documentDao.insertDocumentCategoryOne(id,f1.getName(),FileIOUtil.readFile(f1.getPath()),sid);
                id++;
            }

        }
    }

    @Test
    void gongxin(){
        String path = "E:\\gitt\\springboot_mybatis\\src\\main\\resources\\govement\\工业与信息化部";
        File file = new File(path);
        int id = 32;
        int sid = titleClusterDao.findCategorySidBySname("企业 工业 互联网");

        for (File f:
                file.listFiles()) {
            documentDao.insertDocumentCategoryOne(id,f.getName(),FileIOUtil.readFile(f.getPath()),sid);
            id++;
        }
    }

    @Test
    void cluster1(){
        int sid = titleClusterDao.findCategorySidBySname("企业 工业 互联网");

        List<DocumentCategory> documents = documentDao.findDocumentsByOneId(sid);
        List<List<DocumentCategory>> cluster = algorithmService.cluster(documents, 1.0);
        for (List<DocumentCategory> list : cluster) {

            String s = algorithmService.extractClusteringTopics(list);
            titleClusterDao.insertCategoryLevel(s,sid);
            int twoId = titleClusterDao.findCategorySidBySname(s);
            for (DocumentCategory doc:
                 list) {
                documentDao.updateDocumentCategoryTwo(twoId,doc.getId());
            }
//            String s = algorithmService.extractClusteringTopics(list);
//            System.out.println(s);
        }
    }




    @Test
    void sencondClass(){
        File file = new File("E:\\gitt\\springboot_mybatis\\src\\main\\resources\\govement\\科技厅");
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();

        for (File f1:
             file.listFiles()) {

            String str = SegementUtil.segement(FileIOUtil.readFile(f1.getPath()));
            analyzer.addDocument(f1.getName(),str);


        }
        List<Set<String>> sets = analyzer.repeatedBisection(1.5);
        System.out.println(sets);
    }

    @Test
    void sencondTitle(){
        List<CategoryLink> sids = titleClusterDao.findCategoryByPid(1);
        for (CategoryLink s:
             sids) {
            Integer sid = s.getSid();
            List<DocumentCategory> documentsByOneId = documentDao.findDocumentsByOneId(sid);
            String twoDec = algorithmService.extractClusteringTopics(documentsByOneId);
            titleClusterDao.insertCategoryLevel(twoDec,sid);


        }
    }

    @Test
    void findText(){
        DocumentCategory documentById = documentDao.findDocumentById(13000312);
        System.out.println(documentById.getContent());
    }
    @Test
    void findremarked(){
        List<Integer> arr = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));

        System.out.println(documentService.findRemarkedDocsByIds(arr));
    }

    @Test
    void findTitle() throws IOException {
        List<DocumentCategory> allgeverment = documentDao.findAllgeverment();
        List<String> docs = new ArrayList<>();
        for (DocumentCategory doc:
                allgeverment) {

                String title = doc.getTitle();
                String segement = SegementUtil.segement(title);
                docs.add(segement);
        }
        Map<String, Float> wordsTfIDF = TF_IDF.getWordsTfIDF(docs);
        List<Map.Entry<String, Float>> list = new ArrayList<>(wordsTfIDF.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                if(o2.getValue()>o1.getValue()){
                    return 1;
                }else {
                    return -1;
                }
            }
        });


        for(Map.Entry<String, Float> t:list){
            System.out.println(t.getKey()+":"+t.getValue());
        }
    }

    @Test
    void cluster(){
        List<DocumentCategory> allgeverment = documentDao.findAllgeverment();
        List<List<DocumentCategory>> cluster = algorithmService.cluster(allgeverment, 0.8);
        for (List<DocumentCategory> list : cluster) {
            List<String> res = new ArrayList<>();
            for (DocumentCategory doc:
                 list) {
                res.add(doc.getTitle());
            }
            System.out.println(res);
        }
    }

    @Test
    void clusterGongXin(){
        String path = "E:\\gitt\\springboot_mybatis\\src\\main\\resources\\govement\\工业与信息化部";
        File file = new File(path);
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();

        for (File f:
             file.listFiles()) {
            String name = f.getName();
            String s = FileIOUtil.readFile(f.getPath());
//            for (Document doc :documents) {
//                String str = SegementUtil.segement(doc.getTitle()+doc.getContent());
//                analyzer.addDocument(doc.getId(),str);
//            }
//            List<Set<Integer>> sets = analyzer.repeatedBisection(nclusters);
//            return sets;
            String segement = SegementUtil.segement(name + s);
            analyzer.addDocument(name,segement);
        }
        List<Set<String>> sets = analyzer.repeatedBisection(1.0);
        for (Set<String> s:
             sets) {
            System.out.println(s);
        }

    }

    @Test
    void test(){
        String s = FileIOUtil.readFile("E:\\gitt\\springboot_mybatis\\src\\main\\resources\\govement\\苏经信软件\\关于组织实施2018年软件企业转型升级计划的通知");

        System.out.println(HanLP.extractKeyword(s,5));
    }
}
