package com.HanLpTools;

import com.IRTools.util.SegementUtil;
import com.entity.Document;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * HanLp实现文本聚类
 */
@Service
public class clusterService {
//    public List<Set<Integer>> cluster(List<Document> documents,int nclusters){
//        ClusterAnalyzer<Integer> analyzer = new ClusterAnalyzer<>();
//        for (Document doc :documents) {
//            analyzer.addDocument(doc.getId(),doc.getTitle()+doc.getContent());
//        }
//        List<Set<Integer>> sets = analyzer.repeatedBisection(nclusters);
//        return sets;
//    }
    public List<Set<Integer>> cluster(List<Document> documents,double nclusters){
        ClusterAnalyzer<Integer> analyzer = new ClusterAnalyzer<>();
        for (Document doc :documents) {
            String str = SegementUtil.segement(doc.getTitle()+doc.getContent());
            analyzer.addDocument(doc.getId(),str);
        }
        List<Set<Integer>> sets = analyzer.repeatedBisection(nclusters);
        return sets;
    }


}
