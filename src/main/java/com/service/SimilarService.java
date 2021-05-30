package com.service;

import com.HanLpTools.SimilarityUtil;
import com.IRTools.util.FileIOUtil;
import com.IRTools.util.SimilarUtil;
import com.dao.ClassifictionDao;
import com.dao.DocumentDao;
import com.entity.ClassifiedDocument;
import com.entity.Document;
import com.entity.DocumentCategory;
import com.entity.SimilarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class SimilarService {
    @Autowired
    DocumentDao documentDao;

    @Autowired
    ClassifictionDao classifictionDao;


//    /**
//     * 找出与该文档不同一级分类簇下的相似度最高的n篇文档
//     * @param doc 源文档
//     * @param n 目标文档数量
//     * @return 文档合集
//     */
//    public List<DocumentCategory> similarityBetweenFiles(DocumentCategory doc, int n){
//        List<DocumentCategory> docs = documentDao.findAllDocumentCategory();
//        //找出origin比较文档
//        Queue<SimilarModel> queue = new PriorityQueue<>(new Comparator<SimilarModel>() {
//            @Override
//            public int compare(SimilarModel o1, SimilarModel o2) {
//                return (int) (o2.getSimilar()-o1.getSimilar());
//            }
//        });
//        for (DocumentCategory documentCategory:docs) {
//            if(!documentCategory.getCategoryLevelOneId().equals(doc.getCategoryLevelOneId())){
//                double v = SimilarityUtil.getSimilarity(doc.getContent()+doc.getTitle()
//                        , documentCategory.getContent()+documentCategory.getTitle());
//                if(v<0.3){
//                    continue;
//                }
//
//                SimilarModel model = new SimilarModel();
//                model.setSimilar(v);
//                model.setTarget(documentCategory);
//                queue.add(model);
//            }
//        }
//        List<DocumentCategory> res = new ArrayList<>(n);
//        if(queue.size() == 0){
//            return res;
//        }
//        if(queue.size()<n){
//            n = queue.size();
//        }
//
//        while (n-->0){
//            res.add(queue.poll().getTarget());
//        }
//        return res;
//    }


    public static void main(String[] args) {
        String originPath = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\subClusterData\\盟客币\\[盟, 客, 币, 充值, 需求].txt";
        String path = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\subClusterData";
        String s = FileIOUtil.readFile(originPath);
        File dir = new File(path);
        double similar = 0;
        String name = "";
        for (File f:
             dir.listFiles()) {
            if(!f.getName().equals("盟客币")){
                for (File f1:f.listFiles()) {
                    String readFile = FileIOUtil.readFile(f1.getPath());
                    double v = SimilarityUtil.getSimilarity(s, readFile);
                    if(similar<v){
                        name = f1.getName();
                        similar = v;
                    }
                }
            }
        }
        System.out.println(name);
    }
}
