package com.wang.springboot_mybatis;

import com.IRTools.util.SegementUtil;
import com.Utils.TF_IDF;
import com.controller.DocumentController;
import com.dao.ClassifictionDao;
import com.dao.DocumentDao;
import com.dao.TitleClusterDao;
import com.entity.ClassifiedDocument;
import com.entity.Document;
import com.entity.DocumentCategory;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootTest
public class simuTest {
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

    @Autowired
    DocumentController documentController;

    @Autowired
    AdminService adminService;

    @Test
    void firstClassificition(){
        List<ClassifiedDocument> all = classifictionDao.findAllCls();
        // 分类表中记录一级分类
        Set<String> set = new HashSet<>();
        for (ClassifiedDocument doc:
             all) {
            set.add(doc.getCls());
        }
        List<String> res = new ArrayList<>(set);
        titleClusterService.insertCategoryLevel(res);
        // 文档表中记录一级分类编号
        for (ClassifiedDocument doc:
             all) {
            String cls = doc.getCls();
            int id = titleClusterDao.findCategorySidBySname(cls);
            documentDao.insertDocumentCategoryOne(doc.getId(),doc.getTitle(),doc.getContent(),id);

        }

    }

    @Test
    void secondClassificition(){
        algorithmService.secondClustering();
    }

    @Test
    void test(){
        DocumentCategory documentById = documentDao.findDocumentById(13112908);
        System.out.println(algorithmService.similarityBetweenFiles(documentById, 3));
    }

    @Test
    void comment(){
        documentDao.updateDocumentComment(13000312,"注释");
    }

    @Test
    void findComment(){
        System.out.println(documentController.findComment(13190750));
    }


    @Test
    void title() throws IOException {
        List<DocumentCategory> documents = documentDao.findAllDocumentCategory();
        List<String> docs = new ArrayList<>();
        for (DocumentCategory doc:
             documents) {
            if(doc.getId()>1000){
                String title = doc.getTitle();
                String segement = SegementUtil.segement(title);
                docs.add(segement);
            }

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
    void title11() throws IOException {
        List<DocumentCategory> documents = documentDao.findAllDocumentCategory();
        List<String> docs = new ArrayList<>();
        for (DocumentCategory doc:
                documents) {
            if(doc.getId()>1000){
                String title = doc.getTitle();
                String segement = SegementUtil.segement(title);
                docs.add(segement);
            }

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
    void admin(){
        List<Integer> res = new ArrayList<>();
        List<DocumentCategory> allDocumentCategory = documentDao.findAllDocumentCategory();
        for (DocumentCategory doc:
        allDocumentCategory) {
            if(res.size() > 30){
                System.out.println(res);
                return;
            }
            if(doc.getId()>1000){
                res.add(doc.getId());
            }

        }

    }
    public static void main(String[] args) throws IOException
    {

//        //BufferedReader是可以按行读取文件
//        FileInputStream inputStream = new FileInputStream("E:\\gitt\\springboot_mybatis\\src\\test\\java\\com\\wang\\springboot_mybatis\\123.txt");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//        String str = null;
//        while((str = bufferedReader.readLine()) != null)
//        {
//            String[] split = str.split(" ");
//            System.out.println(split[0]+" :"+split[1]);
//        }
//
//        //close
//        inputStream.close();
//        bufferedReader.close();
       int i = (int)(0.0013006135-0.0013146135);
        System.out.println();

    }

}
