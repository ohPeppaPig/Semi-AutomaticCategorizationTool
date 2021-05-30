package com.IRTools.util;



import com.IRTools.document.Artifact;
import com.IRTools.document.ArtifactsCollection;
import com.IRTools.document.TextDataset;
import com.IRTools.ir.IR;

import java.io.File;

public class SimilarUtil {

    public static double similarity(String origin,String v1,String target,String v2){
        String irModelName = "ir.VSM";

        TextDataset textDataset = new TextDataset();

        Artifact artifact = new Artifact(v1,origin);
        ArtifactsCollection pre = new ArtifactsCollection();
        pre.put(v1,artifact);


        Artifact artifact02 = new Artifact(v2, target);
        ArtifactsCollection post = new ArtifactsCollection();
        post.put(v2,artifact02);

        textDataset.setSourceCollection(pre);
        textDataset.setTargetCollection(post);


//        return IR.computeSingle(textDataset, irModelName);
        IR.compute(textDataset, irModelName);
        return 0;
    }

    /**
     * 比较单个文件 和 单个文件夹里所有文件的平均相似度
     * @param origin
     * @param target
     * @return
     */
    public static double similarityFileAndDir(String origin,String target){
        String irModelName = "ir.VSM";

        //单个文件
        File file = new File(origin);
        Artifact artifact = new Artifact(file.getName(),FileIOUtil.readFile(file.getPath()));
        ArtifactsCollection pre = new ArtifactsCollection();
        pre.put(file.getName(),artifact);
        //文件夹
        TextDataset dataset = new TextDataset();
        dataset.setSourceCollection(pre);
        dataset.setTargetCollection(FileIOUtil.getCollections(target, ".txt"));

        return IR.computeAvg(dataset, irModelName);
    }


    public static void similarityWithFile(String file1,String file2){
        String irModelName = "ir.VSM";

        TextDataset dataset = new TextDataset();
        dataset.setSourceCollection(getCollections(file1));
        dataset.setTargetCollection(getCollections(file2));
        IR.compute(dataset,irModelName);
    }

    public static ArtifactsCollection getCollections(String dirPath) {
        File dirFile = new File(dirPath);

        ArtifactsCollection collections = new ArtifactsCollection();
        for (File f : dirFile.listFiles()) {
            String id = f.getName();
            Artifact artifact = new Artifact(id, FileIOUtil.readFile(f.getPath()));
            collections.put(id, artifact);

        }
        return collections;
    }

    public static void main(String[] args) {
        String a = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\classification\\衣食住行\\【衣食住行】第十一周优化（衣食住行任务结束时间；生产物资；交易报表）.txt";
        String b = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\classification\\衣食住行";
//        String b = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\classification\\衣食住行";

        double v = similarityFileAndDir(a, b);
        System.out.println(v);
    }
}
