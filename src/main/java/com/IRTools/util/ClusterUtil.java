package com.IRTools.util;


import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;

import java.io.File;

public class ClusterUtil {
    public static ClusterAnalyzer<String> analyzer;
    public static ClusterAnalyzer<String> addFileToCluster(String path){
        analyzer = new ClusterAnalyzer<String>();
        addDir(path);
        return analyzer;
    }
    public static void addDirectory(String path){
        File file = new File(path);
        File[] childs = file.listFiles();
        int flag = 0;
        for (File ch:childs) {
            flag +=1;
            File[] files = ch.listFiles();
            for (File f : files) {
                String name = flag+"-"+f.getName();
                String str = FileIOUtil.readFile(f.getPath());
                analyzer.addDocument(name,str);
            }

        }
    }

    public static void addDir(String path){
        File file = new File(path);
        File[] childs = file.listFiles();
        for (File ch:childs) {
            String name = ch.getName();
            String str = FileIOUtil.readFile(ch.getPath());

            analyzer.addDocument(name,str);
        }
    }

}
