package com.IRTools.preprocess;



import com.IRTools.util.FileIOUtil;
import com.IRTools.util.SimilarUtil;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class CatalogPreprecess {
    public static void main(String[] args) {
        secPredict();
    }


    /**
     * 无法按照名称进行分类的文档进行预测分类
     */

    public static void secPredict(){
        // 将文件内容进行分类
        List<String> classification = Arrays.asList("物资库", "衣食住行", "成长伙伴","物资模块");
        // 遍历文件 ，按照名称进行分类
        String path = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\preprocess_issue";
        File file = new File(path);
        File[] childs = file.listFiles();

        for (File ch:childs) {
            boolean flag = false;
            String name = ch.getName();
            for (String cls:
                    classification) {
                boolean contains = name.contains(cls);
                if (contains){
                    flag = true;
                    break;
//                    String str = FileIOUtil.readFile(ch.getPath());
//                    String p = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\classification\\";
//                    FileIOUtil.writeFile(str,p+cls+"/"+name);
//                    break;
                }
            }
            if(!flag){
                // 相似度比较预测
                String p = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\classification";
                File f = new File(p);
                File[] clds = f.listFiles();
                TreeMap<Double,String> map = new TreeMap<>(new Comparator<Double>() {
                    @Override
                    public int compare(Double o1, Double o2) {
                        return (int)(o2-o1);
                    }
                });
                for (File cl :
                        clds) {
                    if(cl.isDirectory()&&!cl.getName().equals("未分类")){
                        double v = SimilarUtil.similarityFileAndDir(ch.getPath(), cl.getPath());
                        System.out.println(name+":"+cl.getName()+":"+v);
                        map.put(v,cl.getName());
                    }
                }

            }

        }
    }

    /**
     * 初步对文档的名称进行首次分类
     */
    public static void preProduceByname(){
        // 将文件内容进行分类
        List<String> classification = Arrays.asList("物资库", "衣食住行", "成长伙伴","物资模块");
        // 遍历文件 ，按照名称进行分类
        String path = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\preprocess_issue";
        File file = new File(path);
        File[] childs = file.listFiles();
        for (File ch:childs) {
            String name = ch.getName();
            for (String cls:
                    classification) {
                boolean contains = name.contains(cls);
                if (contains){
                    String str = FileIOUtil.readFile(ch.getPath());
                    String p = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\classification\\";
                    FileIOUtil.writeFile(str,p+cls+"/"+name);
                    break;
                }
            }

        }
    }
}
