package com.wang.springboot_mybatis;

import com.HanLpTools.SimilarityUtil;
import com.IRTools.util.FileIOUtil;
import com.IRTools.util.SegementUtil;
import com.Utils.CalculateUtil;
import com.Utils.urlUtil;
import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerOutput;
import com.dao.ClassifictionDao;
import com.dao.DocumentDao;
import com.dao.TitleClusterDao;
import com.entity.CategoryLink;
import com.entity.Document;
import com.entity.DocumentCategory;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import com.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@SpringBootTest
class SpringbootMybatisApplicationTests {

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
    void contextLoads() {
        int i = service.find();
        System.out.println(i);
    }


    @Test
    /**
     * 测试空白文档
     */
    void blankText(){
        List<Document> documents = documentService.blankText();
        System.out.println(documents);
        System.out.println(documents.size());
    }

    @Test
    void insertNoBlankText(){
//        documentService.insertNoBlankText();
//        Document doc = new Document();
//        doc.setId(1);
//        doc.setTitle("123");
//        doc.setContent("123");
        documentService.insertNoBlankText();
    }

//    @Test
//    void cluster(){
//        algorithmService.cluster();
//    }

    @Test
    void urlExtract(){
        List<String> strings = documentService.urlExtract();
        for (String s:
             strings) {
            FileIOUtil.appendFile("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\Utils\\url.txt",s+"\n");
        }
    }

    @Test
    void classifiction(){

        classifictionService.classifiction();
    }
    @Test
    void predict() throws IOException {
        classifictionService.predict();
    }

    @Test
    void insertCls() throws IOException {
//        classifictionDao.insertCls(1,"1","1","1");
        classifictionService.insertClsDetails();
    }

    @Test
    void selectCls(){
        List<Document> documentByCls = classifictionDao.findDocumentByCls("智慧城市");
        System.out.println(documentByCls);
    }



//    @Test
//    void similar(){
//        List<Document> documents = similarService.similarityBetweenFiles(13020016, 3);
//        System.out.println(documents);
//
//    }




    @Test
    void clusterAll(){
        String path = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\simu_data";
        File file = new File(path);
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();

        for (File f:file.listFiles()) {
            for (File f1:f.listFiles()){
                String s = FileIOUtil.readFile(f1.getPath());
                String str = SegementUtil.segement(f1.getName()+s);
                analyzer.addDocument(f1.getName(),str);
            }
        }
        List<Set<String>> sets = analyzer.repeatedBisection(2.0);
        for (Set<String> set:sets) {
            System.out.println(set);
        }
    }

    @Test
    void similarFileAndFiles(){
        String path = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\simu_data\\衣食住行\\【衣食住行】 盟客币优惠";
        String filePath = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\simu_data\\成长伙伴";
        File file = new File(filePath);
        for (File f:file.listFiles()) {
            String s = FileIOUtil.readFile(path);
            String str = f.getName()+FileIOUtil.readFile(f.getPath());
            System.out.println(SimilarityUtil.getSimilarity(s, str));
        }
    }

    @Test
    void cluster123(){
        File file = new File("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\simu_data\\产学研教");
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<>();
        for (File f:file.listFiles()){
            String s = FileIOUtil.readFile(f.getPath());
            String segement = SegementUtil.segement(s);
            analyzer.addDocument(f.getName(),segement);
        }
        List<Set<String>> set1 = analyzer.repeatedBisection(1.2);
        List<Set<String>> set2 = analyzer.repeatedBisection(1.8);
        List<Set<String>> set = new ArrayList<>();
        for (Set<String> s1:set1) {
            for (Set<String> s2:set2 ){
                if(CalculateUtil.isSetEqual(s1,s2)){
                    set.add(s1);
                }
            }
        }
        System.out.println(set);
//        List<Set<String>> set = new ArrayList<>();
//        set.addAll(set1);
//        set.retainAll(set2);
//        System.out.println(set);
    }

    @Test
    void firstLevelClustering(){
        List<Document> allTextDetail = documentDao.findAllTextDetail();
        HashMap<String,Integer> map = new HashMap<>();
        for (Document doc:allTextDetail) {
            List<String> strings = urlUtil.extractMessageByRegular(doc.getTitle());
            if(strings.size()==0){
                continue;
            }
            map.merge(strings.get(0), 1, (a, b) -> a + b);

        }

        Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Integer> entry = entries.next();
            System.out.println( entry.getKey() + ":  " + entry.getValue());

        }

    }



//    @Test
////    void firstLevelClustering01(){
////        List<Document> allTextDetail = documentDao.findAllTextDetail();
////        HashMap<String,Integer> map = new HashMap<>();
////        StringBuilder sb = new StringBuilder();
////        for (Document doc:allTextDetail) {
////            sb.append(doc.getTitle()).append(" ");
////        }
////        String s = sb.toString();
////        String segement = SegementUtil.segement(s);
////        List<String> strings = HanLP.extractKeyword(segement, 20);
////        System.out.println(strings);
////    }


    public static void main(String[] args) throws IOException {

        String text="一、产品功能更新简报1. 新增共享空间模块  2. 企业、个人主页交互升级    3. Web左侧会话列表支持按盟分组、按时间排序；4. 盟客币精确到分-订单支付支持盟客币100%抵扣；5. 优化： -已支付但未完成的订单提示文案优化；-扫一扫后退统一回到扫一扫上一页面；二、沟通事宜1. 农场2. 盟客世界3. 共享空间三、会议纪要时间：2019年8月2日地点：38楼会议室主持人：杜波记录：周蕴颖     参加人员：林佑、杜波、周蕴颖、刘劭、方燕玉、周颖婷、黄璜、周鹏、杨鹏程、张天、朱荣鑫、曾泽堂、周晨敏、刘晓宇、王振伍  餐厅近期需求讨论；近期产品工作沟通：（1）登录注册；（2）加好友与去好友再加好友；（3）点赞角色，是否考虑去掉企业角色，改成只有用户点赞（马甲也可以点赞）；空间、企业、个人的相似点和区别；空间管理员要多个（商会场景）；空间运营，目前可以采用员工认领的方式；HR和CRM；“内部结算”文案要改成类似“集团模式”、“独立模式（非集团）”；所以盟设置那个开关，不仅影响财务模块；共享雨伞目前进度沟通；农场的目标、任务模块要与一块地的具体工作挂钩，有了上游之后，和餐厅对接也会方便；";
        StringReader sr=new StringReader(text);
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        while((lex=ik.next())!=null){
            System.out.print(lex.getLexemeText()+"|");
        }
    }
    @Test
    void test(){
        System.out.println(titleClusterService.findAllMenu());

    }


}
