package com.service;

import com.HanLpTools.classifictionUtil;
import com.IRTools.util.FileIOUtil;
import com.Utils.urlUtil;
import com.dao.ClassifictionDao;
import com.dao.DocumentDao;
import com.dao.TitleClusterDao;
import com.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class classifictionService {
    @Autowired
    ClassifictionDao classifictionDao;

    @Autowired
    DocumentDao documentDao;

    @Autowired
    TitleClusterDao titleClusterDao;

    /**
     * 文档预测分类
     * @param content 文本内容
     * @return 分类名称
     */
    public String docPredict(String content) throws IOException {
        return classifictionUtil.predict(content);
    }

    public void classifiction(){
        String name = "衣食住行";
        List<Document> documents = classifictionDao.fuzzyQuery(name);
        for (Document document: documents) {
            String title = document.getTitle();
            String input = document.getContent();
            String path = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\simu_data\\盟客网";
            FileIOUtil.writeFile(input,path+"/"+title);

        }
    }

    public void predict() throws IOException {
        List<Document> documents = classifictionDao.predict();
        for (Document document: documents){
            String title = document.getTitle();
            String input = document.getContent()+title;
            String predict = classifictionUtil.predict(input);
            FileIOUtil.appendFile("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\service\\predict.txt",title+":  "+predict+" :"+document.getContent()+"\n");

        }
    }

    public void insertCls() throws IOException {
        List<Document> documents = documentDao.findAllTextDetail();
        for (Document document: documents){
            Integer id = document.getId();
            String title = document.getTitle();
            String content = document.getContent();
            String predict = classifictionUtil.predict(title + content);
            classifictionDao.insertCls(id,title,content,predict);
        }

    }


    public void insertClsDetails() throws IOException {
        List<Document> documents = documentDao.findAllTextDetail();
        for (Document document: documents){
            Integer id = document.getId();
            String title = document.getTitle();
            String content = document.getContent();
            String cls;
            if(title.contains("产学研教")){
                cls = "产学研教";
            }else if (title.contains("思目论坛")){
                cls = "思目论坛";
            }else if(title.contains("成长伙伴")){
                cls = "成长伙伴";
            }else if(title.contains("智慧城市")||title.contains("盟客世界")){
                cls = "智慧城市";
            }else if(title.contains("物资")){
                cls = "物资";
            }else if(title.contains("盟客币")){
                cls = "盟客币";
            }else if(title.contains("盟客网")){
                cls = "盟客网";
            }else if(title.contains("衣食住行")){
                cls = "衣食住行";
            }else if(title.contains("超级账号")){
                cls = "超级账号";
            }else if(title.contains("产品")||title.contains("会议")){
                cls = "产品会议";
            }else if(title.contains("清单")||title.contains("待办需求")){
                cls = "需求记录清单";
            }else if(title.contains("培训")){
                cls = "培训相关";
            }else if(title.contains("旅游")){
                cls = "旅游申请";
            }else {
                cls = classifictionUtil.predict(title + content);
            }
            classifictionDao.insertCls(id,title,content,cls);
        }
    }

    /**
     * 自动扫描数据库中的文档可能的一级标题
     * 返回用户进行确认
     */
    public Map<String,Integer> extractTitle(){
        List<Document> allTextDetail = documentDao.findAllTextDetail();
        HashMap<String,Integer> map = new HashMap<>();

        List<String> titles = new ArrayList<>();
        // 找出所有的人工标记标题
        for (Document doc:allTextDetail) {
            List<String> strings = urlUtil.extractMessageByRegular(doc.getTitle());
            if(strings.size()==0){
                continue;
            }
            titles.add(strings.get(0));

        }
        // 再次扫描找出关键字个数
        for (Document doc:allTextDetail){
            for(String title:titles){
                if(doc.getTitle().contains(title)){
                    map.merge(title, 1, (a, b) -> a + b);
                    break;
                }
            }

        }
        return map;

//        Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
//        while (entries.hasNext()) {
//            Map.Entry<String, Integer> entry = entries.next();
//            System.out.println( entry.getKey() + ":  " + entry.getValue());
//
//        }
    }

    /**
     * 将文档进行一级分类存入数据库
     * @param strs
     */
    public void firstClassification(List<String> strs) throws IOException {
        List<Document> allTextDetail = documentDao.findAllTextDetail();

        String title = "";
        for (Document doc:allTextDetail) {
            for (String str:
                 strs) {
                // 文档名称匹配title
                if(doc.getTitle().contains(str)){
                    title = str;
                    break;
                }
            }
            if("".equals(title)){
                // 进行预测分类
                title = classifictionUtil.predict(doc.getTitle()+doc.getContent());
            }
            int categoryLevelOneId = titleClusterDao.findCategorySidBySname(title);
            documentDao.insertDocumentCategoryOne(doc.getId(),doc.getTitle(),doc.getContent(),categoryLevelOneId);

        }

    }

}
