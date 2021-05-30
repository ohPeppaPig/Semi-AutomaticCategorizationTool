package com.service;

import com.Utils.urlUtil;
import com.VO.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.DocumentDao;
import com.dao.DocumentHistoryDao;
import com.dao.TitleClusterDao;
import com.entity.*;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.util.URLUtil;

import java.util.*;

@Service
public class DocumentService {

    @Autowired
    DocumentDao documentDao;
    @Autowired
    DocumentHistoryDao documentHistoryDao;
    @Autowired
    AlgorithmService algorithmService;
    @Autowired
    SimilarService similarService;
    @Autowired
    TitleClusterDao titleClusterDao;
    @Autowired
    TitleClusterService titleClusterService;

    public int docNums(){
        return documentDao.findAll();
    }

    public List<Document> findAllText(){
        List<Document> allTextDetail = documentDao.findAllTextDetail();
        return allTextDetail;
    }

    /**
     * 查找空白文档详细信息
     * @return
     */
    public List<Document> blankText(){
        List<Document> allText = documentDao.findAllText();
        List<Document> blankText = new ArrayList<>(allText.size()/3);
        for (Document doc:allText) {
            String content = doc.getContent();
            JSONObject jsonObject = JSON.parseObject(content);

            JSONArray blocks = JSONArray.parseArray(jsonObject.getString("blocks"));
            if(blocks.size()<=2&&!content.contains("src")){
                blankText.add(doc);
            }

        }
        return blankText;
    }

    /**
     * 将非空文档的json content 转化为文本
     */
    public void insertNoBlankText(){
        List<Document> allText = documentDao.findAllText();
        for (Document doc:allText) {
            String content = doc.getContent();
            JSONObject jsonObject = JSON.parseObject(content);

            JSONArray blocks = JSONArray.parseArray(jsonObject.getString("blocks"));
            if(blocks.size()<=2){
               continue;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < blocks.size(); i++) {
                JSONObject json = blocks.getJSONObject(i);
                String pic = json.getString("text");
                sb.append(pic);
            }
            doc.setContent(sb.toString());
            documentDao.insertDocumentText(doc.getId(),doc.getTitle(),doc.getContent());
        }
    }

    /**
     * 提取文档的url链接
     *
     */
    public List<String> urlExtract(){
        List<Document> allText = documentDao.findAllTextSrc();
        List<String> res = new ArrayList<>(allText.size()/2);
        for (Document doc:
             allText) {
            if(doc.getContent().contains("entityMap")){
                List<String> strings = urlUtil.urlExtract(doc.getContent());
                System.out.println(strings);
                if(strings!=null){
                    res.addAll(strings);
                }
            }

        }
        return res;
    }

    /**
     * 查看二级标题下的文档信息
     * @param id
     * @return
     */
   public List<DocumentCategory> findDocumentByTwoId(int id){
        return documentDao.findDocumentsByTwoId(id);
   }

    /**
     * 查找文档的详细信息 与 相似文档
     * @param id
     * @return
     */
   public DocumentDetail findDocumentDetail(int id){
       DocumentDetail documentDetail = new DocumentDetail();
       DocumentCategory document = documentDao.findDocumentById(id);
       String s = algorithmService.extractkeySentence(document);
       documentDetail.setKeySentence(s);
       documentDetail.setTitle(document.getTitle());
       documentDetail.setId(id);
       documentDetail.setContent(document.getContent());

//        查找不同一级分类下的相似的文档
//       List<DocumentCategory> documentCategories = algorithmService.similarityBetweenFiles(document, 3);
//       if(documentCategories==null){
//           return null;
//       }
//
//       List<DocumentSimple> documentSimpleList = new ArrayList<>();
//       for (DocumentCategory docs:
//       documentCategories) {
//           DocumentSimple documentSimple = new DocumentSimple();
//
//           documentSimple.setId(docs.getId());
//           documentSimple.setTitle(docs.getTitle());
//           documentSimple.setKeySentences(algorithmService.extractkeySentence(docs));
//           documentSimple.setCategoryOneName(titleClusterDao.findCategoryNameById(docs.getCategoryLevelOneId()));
//           documentSimple.setCategorytwoName(titleClusterDao.findCategoryNameById(docs.getCategoryLevelTwoId()));
//           documentSimpleList.add(documentSimple);
//
//       }
////
//       documentDetail.setSimilaryDocs(documentSimpleList);
       return documentDetail;


   }

    /**
     * 动态调整二级分类簇
     * @param oneId 一级分类id
     * @param n 调整比例值
     * @return diff 信息
     */
    public DocumentClusterDiffVo dynamicAdjustCluster(int oneId, int n){
        DocumentClusterDiffVo documentClusterDiffVo = new DocumentClusterDiffVo();
//        documentClusterDiffVo.setSecondaryMenuVoList();
        // 调整新的分类簇信息

        // 查找一级分类文档

        List<DocumentCategory> documentsByOneId = documentDao.findDocumentsByOneId(oneId);
        double clusterArgs = 1.5 + ((double) n-50)/100;

        // 进行聚类
        List<List<DocumentCategory>> lists = algorithmService.cluster(documentsByOneId, clusterArgs);
        // 对聚类的文档提取关键词与关键句 并记录
        List<SecondaryMenuVo> after = new ArrayList<>();
        for (List<DocumentCategory> list:
             lists) {
            SecondaryMenuVo secondaryMenuVo = new SecondaryMenuVo();
            // 提取关键字
            String keyWords = algorithmService.extractClusteringTopics(list);
            // 提取关键句
            String sentence = algorithmService.extractkeySentence(list);
            secondaryMenuVo.setCategoryTwo(new CategoryLink(0,keyWords,0));
            secondaryMenuVo.setDescription(sentence);
            after.add(secondaryMenuVo);

        }

        // 找到原先的二级分类描述信息

        List<SecondaryMenuVo> pre = titleClusterService.secondaryMenu(oneId);
        documentClusterDiffVo.setSecondaryMenuVoList(after);
        documentClusterDiffVo.setDiffPre(diffClusters(pre,after));
        documentClusterDiffVo.setDiffAfter(diffClusters(after,pre));

        return documentClusterDiffVo;
    }

    private List<SecondaryMenuVo> diffClusters(List<SecondaryMenuVo> a,List<SecondaryMenuVo> b){
        List<String> temp = new ArrayList<>();
        List<SecondaryMenuVo> res = new ArrayList<>();
        for (SecondaryMenuVo secondaryMenuVo:
             b) {
            temp.add(secondaryMenuVo.getCategoryTwo().getSname());
        }

        for (SecondaryMenuVo secondaryMenuVo:
             a) {
            if(!temp.contains(secondaryMenuVo.getCategoryTwo().getSname())){
                res.add(secondaryMenuVo);
            }
        }
        return res;
    }

    public void updateDocumentComment(int id,String comment){
        documentDao.updateDocumentComment(id,comment);
    }

    public String findDocumentComment(int id){
        return documentDao.findDocumentComment(id);
    }

    public DetailedDocumentVo findAllContents(int id){
        DocDetail docDetail = documentDao.findAllContents(id);
        DetailedDocumentVo documentVo = new DetailedDocumentVo();
        documentVo.setTitle(docDetail.getTitle());
        documentVo.setContent(docDetail.getContent());
//        String[] comments = docDetail.getComment().split(",");
        String comment = docDetail.getComment();
        List<String> comments = new ArrayList<>();
        if(comment!=null){
            String[] c = comment.split(",");
            comments.addAll(Arrays.asList(c));
        }

        String urls = docDetail.getUrl();
        List<String> urlList = new ArrayList<>();
        if(urls!=null){
            String[] split = urls.split(",");
            urlList.addAll(Arrays.asList(split));
        }
        documentVo.setComments(comments);
        documentVo.setUrls(urlList);
        documentVo.setKeySentence(algorithmService.extractkeySentence(docDetail.getContent()));
        return documentVo;
    }

    /**
     * 查找标注过的文档集
     * @return
     */
    public List<RemarkedDocumentVo> findRemarkedDocs(){
        List<RemarkedDoc> remarkedDocs = documentDao.findRemarkedDocumentVo();
        List<RemarkedDocumentVo> res = new ArrayList<>(remarkedDocs.size());
        for (RemarkedDoc doc:
             remarkedDocs) {
            RemarkedDocumentVo remarkedDocumentVo = new RemarkedDocumentVo();
            remarkedDocumentVo.setId(doc.getId());
            remarkedDocumentVo.setTitle(doc.getTitle());
            remarkedDocumentVo.setComment(doc.getComment());
            remarkedDocumentVo.setCategoryOne(titleClusterDao.findCategoryNameById(doc.getCategoryLevelOneId()));
            remarkedDocumentVo.setCategoryTwo(titleClusterDao.findCategoryNameById(doc.getCategoryLevelTwoId()));
            res.add(remarkedDocumentVo);
        }
        return res;
    }

    public void delComment(int id){
        documentDao.delComment(id);
    }

    public void updateCategory(List<Integer> ids,int twoId){
        for (int id :
                ids) {
            documentDao.updateCategoryTwoTitle(id,twoId);
        }
    }

    public List<RemarkedDocumentVo> findRemarkedDocsByIds(List<Integer> ids){
        List<RemarkedDoc> remarkedDocs = new ArrayList<>(ids.size());
        for (int id:
             ids) {
            RemarkedDoc vo = documentDao.findRemarkedDocumentVoById(id);
            remarkedDocs.add(vo);
        }
        List<RemarkedDocumentVo> res = new ArrayList<>(remarkedDocs.size());
        for (RemarkedDoc doc:
                remarkedDocs) {
            RemarkedDocumentVo remarkedDocumentVo = new RemarkedDocumentVo();
            remarkedDocumentVo.setId(doc.getId());
            remarkedDocumentVo.setTitle(doc.getTitle());
            remarkedDocumentVo.setComment(doc.getComment());
            remarkedDocumentVo.setCategoryOne(titleClusterDao.findCategoryNameById(doc.getCategoryLevelOneId()));
            remarkedDocumentVo.setCategoryTwo(titleClusterDao.findCategoryNameById(doc.getCategoryLevelTwoId()));
            res.add(remarkedDocumentVo);
        }
        return res;
    }

    public static void main(String[] args) {
        String url = "1,2";
        System.out.println(url.split(",")[1]);
    }



}
