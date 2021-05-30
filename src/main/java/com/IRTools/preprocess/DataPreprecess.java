package com.IRTools.preprocess;



import com.IRTools.util.GetClassFileUtil;
import com.IRTools.util.TransferToTxtUtil;

import java.io.File;

public class DataPreprecess {


    private static String projectPath = "data/iTrust-master";
    private static String ucPath = "data/uc_origin";

    private static String classPath = "result/class";
    private static String classTxtPath =  "result/class_txt";
    private static String classProcessedPath =  "result/class_preprocessed";
    private static String ucPreProcessedPath =  "result/uc_preprocessed";





    private TransferToTxtUtil getSrcTXT = new TransferToTxtUtil();

    public static String getClassProcessedPath() {
        return classProcessedPath;
    }

    public static void setClassProcessedPath(String classProcessedPath) {
        DataPreprecess.classProcessedPath = classProcessedPath;
    }

    public static String getUcPreProcessedPath() {
        return ucPreProcessedPath;
    }

    public static void setUcPreProcessedPath(String ucPreProcessedPath) {
        DataPreprecess.ucPreProcessedPath = ucPreProcessedPath;
    }

    private void cleanData() {
        deleteFileInDir(classPath);
        deleteFileInDir(classTxtPath);
        deleteFileInDir(classProcessedPath);
        deleteFileInDir(ucPreProcessedPath);
    }

    public void deleteFileInDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        } else {
            for (File f : files) {
                f.delete();
            }
        }
    }

    public void arrangeData() {
        try {
            cleanData();
            GetClassFileUtil.getClassFromProject(projectPath, classPath);
            getSrcTXT.transferTXT(classPath, classTxtPath);
            BatchingPreprocess preprocess = new BatchingPreprocess(ucPath, classTxtPath, classProcessedPath, ucPreProcessedPath);
            preprocess.doProcess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void arrangeChineseData(String origin,String target){
        try {
            deleteFileInDir(target);
            ChineseSplit chineseSplit = new ChineseSplit(origin, target);
            chineseSplit.doProcess();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

//        long startTime = System.currentTimeMillis();
//        DataPreprecess dataProcess = new DataPreprecess();
//        dataProcess.arrangeData();
//        long endTime = System.currentTimeMillis();
//        System.out.println("time cost:" + (endTime - startTime) * 1.0 / 1000 / 60);
        DataPreprecess dataPreprecess = new DataPreprecess();
        String origin = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\dataMini\\体育";
        String target = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\result";
//        dataPreprecess.arrangeChineseData(origin,ta);
    }

}
