package com.service;

import com.HanLpTools.SimilarityUtil;
import com.HanLpTools.clusterService;
import com.IRTools.util.FileIOUtil;
import com.IRTools.util.SegementUtil;
import com.dao.ClassifictionDao;
import com.dao.DocumentDao;
import com.dao.TitleClusterDao;
import com.entity.Document;
import com.entity.DocumentCategory;
import com.entity.SimilarModel;
import com.hankcs.hanlp.HanLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class AlgorithmService {

    @Autowired
    DocumentDao documentDao;

    @Autowired
    clusterService clusterService;

    @Autowired
    ClassifictionDao classifictionDao;

    @Autowired
    TitleClusterDao titleClusterDao;
    private static double Threshold = 0;


    /**
     * 模拟聚类
     */
//    public void cluster(){
//        List<Document> allTextDetail = documentDao.findAllTextDetail();
//        Map<Integer,String> map = new HashMap<>(allTextDetail.size());
//
//        for (Document doc:
//             allTextDetail) {
//            map.put(doc.getId(),doc.getTitle());
//        }
//
//        List<Set<Integer>> clusterLists = clusterService.cluster(allTextDetail,7);
//        System.out.println(clusterLists.size());
//
//        for (Set<Integer> sets:
//             clusterLists) {
//            for (int id:
//                 sets) {
//                FileIOUtil.appendFile("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\service\\2.txt",map.get(id));
//                FileIOUtil.appendFile("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\service\\2.txt","\\\n");
//
//            }
//            String s = "______________________________________________________"+"\\\n";
//            FileIOUtil.appendFile("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\service\\2.txt",s);
//        }
//
//    }

    /**
     * 分类查找文档
     * @param cls
     * @return
     */
    public List<Document> findDocumentBYCls(String cls){
        List<Document> documentByCls = classifictionDao.findDocumentByCls(cls);
        if(documentByCls!=null){
            return documentByCls;
        }
        return null;
    }


    /**
     * 聚类demo
     */
//    public void subCluster(){
//        String cls = "衣食住行";
//        List<Document> documentBYCls = findDocumentBYCls(cls);
//        Map<Integer,String> map = new HashMap<>(documentBYCls.size());
//
//        for (Document doc:
//                documentBYCls) {
////            List<String> phraseList = HanLP.extractPhrase(doc.getContent(), 5);
//
//            map.put(doc.getId(),doc.getTitle()+": "+doc.getContent());
//        }
//        List<Set<Integer>> clusterLists = clusterService.cluster(documentBYCls,1.8);
////        int count = 0;
//        String p = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\subClusterData\\"+cls;
//        FileIOUtil.deleteFileInDir(p);
//        for (Set<Integer> sets:
//                clusterLists) {
////            count++;
////            String path = p+"\\"+count+".txt";
//            List<String> docs = new ArrayList<>();
//            for (int id:
//                    sets) {
//                docs.add(SegementUtil.segement(map.get(id)));
//            }
//            List<String> topics = extractClusteringTopics(docs);
//            String path = p+"\\"+topics.toString()+".txt";
//            for (int id:
//                    sets) {
//                FileIOUtil.appendFile(path,map.get(id)+"\n");
//
//            }
//        }
//
//    }

    /**
     * 提取一组文档的关键字
     * @param docs
     * @return
     */
    public String extractClusteringTopics(List<DocumentCategory> docs){

        StringBuilder sb = new StringBuilder();
        for (DocumentCategory doc:docs) {
            String segement = SegementUtil.segement(doc.getTitle() + doc.getContent());
            List<String> keyword = HanLP.extractKeyword(segement, 5);
            sb.append(keyword.toString());
        }
       return String.join(",", HanLP.extractKeyword(sb.toString(),5));
    }


    /**
     * 提取一组文档的关键句
     * @param docs
     * @return
     */
    public String extractkeySentence(List<DocumentCategory> docs){
        StringBuilder sb = new StringBuilder();
        for (DocumentCategory doc:docs) {
            sb.append(doc.getContent());
        }
        List<String> strings = HanLP.extractSummary(sb.toString(), 1);
        return String.join(",", strings);
    }

    /**
     * 提取一篇文档的关键句
     * @param docs
     * @return
     */
    public String extractkeySentence(DocumentCategory docs){

        List<String> strings = HanLP.extractSummary(docs.getContent(), 2);
        return String.join(",", strings);
    }

    public String extractkeySentence(String docs){
        List<String> strings = HanLP.extractSummary(docs, 3);
        return String.join(",",strings);
    }

    /**
     * 文档进行非监督聚类
     * @param docs 文档簇
     * @param n 聚类参数
     * @return 聚类结果集
     */
    public List<List<DocumentCategory>> cluster(List<DocumentCategory> docs,double n){
        Map<Integer,DocumentCategory> map = new HashMap<>(docs.size());

        for (DocumentCategory doc:
                docs) {
            map.put(doc.getId(),doc);
        }

        // DocumentCategory 转为 Document
        List<Document> documents = new ArrayList<>();
        for (DocumentCategory documentCategory:
        docs) {
            Document doc = new Document();
            doc.setId(documentCategory.getId());
            doc.setTitle(documentCategory.getTitle());
            doc.setContent(documentCategory.getContent());
            documents.add(doc);
        }

        List<Set<Integer>> clusterLists = clusterService.cluster(documents,n);
        List<List<DocumentCategory>> res = new ArrayList<>(clusterLists.size());
        // 将聚类的id 转为 document
        for (Set<Integer> sets:
             clusterLists) {
            List<DocumentCategory> d = new ArrayList<>();
            for (int id:
                 sets) {
                DocumentCategory document = map.get(id);
                d.add(document);
            }
            res.add(d);
        }
        return res;
    }


    /**
     * 对一级分类的数据进行适当参数的聚类整理
     * 并写入文档的二级分类id
     */
    public void secondClustering(){
        List<DocumentCategory> allDocumentCategory = documentDao.findAllDocumentCategory();
        // 按一级分类id 进行内存存储
        Map<Integer,List<DocumentCategory>> map = new HashMap<>();

        for (DocumentCategory documentCategory:
        allDocumentCategory) {
            int levelOneId = documentCategory.getCategoryLevelOneId();
            if(!map.containsKey(levelOneId)){
                List<DocumentCategory> temp = new ArrayList<>();
                temp.add(documentCategory);
                map.put(levelOneId,temp);
            }else {
                map.get(levelOneId).add(documentCategory);
            }
        }

        // 遍历map,对每一个一级分类数据进行二次聚类
        Iterator<Map.Entry<Integer, List<DocumentCategory>>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, List<DocumentCategory>> entry = entries.next();
            Integer oneId = entry.getKey();
            List<DocumentCategory> value = entry.getValue();

            List<List<DocumentCategory>> lists = cluster(value, 1.5);
            // 对每个簇进行提取关键词做标识
            for (List<DocumentCategory> docs:
                 lists) {
                String keyWords = extractClusteringTopics(docs);
                // 二级分类 写入 分类数据库
                titleClusterDao.insertCategoryLevel(keyWords,oneId);
                //记录二级分类id
                int twoId = titleClusterDao.findCategorySidBySname(keyWords);
                // 遍历分簇下 所有的文档 写入二级分类
                for (DocumentCategory doc:docs){
                    documentDao.updateDocumentCategoryTwo(twoId,doc.getId());
                }
            }

        }
    }

    /**
     * 找出与该文档不同一级分类簇下的相似度最高的n篇文档
     * @param doc 源文档
     * @param n 目标文档数量
     * @return 文档合集
     */
    public List<DocumentCategory> similarityBetweenFiles(DocumentCategory doc, int n){
        List<DocumentCategory> docs = documentDao.findAllDocumentCategory();
        //找出origin比较文档
        Queue<SimilarModel> queue = new PriorityQueue<>((o1, o2) -> (int) (o2.getSimilar()-o1.getSimilar()));
        for (DocumentCategory documentCategory:docs) {
            if(!documentCategory.getCategoryLevelOneId().equals(doc.getCategoryLevelOneId())){
                double v = SimilarityUtil.getSimilarity(doc.getContent()+doc.getTitle()
                        , documentCategory.getContent()+documentCategory.getTitle());
                if(v<Threshold){
                    continue;
                }
                if(queue.size()==n&&v>queue.peek().getSimilar()){
                    queue.poll();
                    SimilarModel model = new SimilarModel();
                    model.setSimilar(v);
                    model.setTarget(documentCategory);
                    queue.add(model);
                }
            }
        }
        List<DocumentCategory> res = new ArrayList<>(n);
        if(queue.size() == 0){
            return res;
        }
        if(queue.size()<n){
            n = queue.size();
        }

        while (n-->0){
            res.add(queue.poll().getTarget());
        }
        return res;
    }
}
