package com.IRTools.preprocess;



import com.IRTools.util.FileIOUtil;

import java.io.File;

public class BatchingPreprocess {
    private File ucPath;
    private File classTxtPath;
    private File classProcessedPath;
    private File ucPreProcessedPath;

    public BatchingPreprocess(String ucPath, String classTxtPath, String classProcessedPath, String ucPreProcessedPath) {
        this.ucPath = new File(ucPath);
        this.classTxtPath = new File(classTxtPath);
        this.ucPreProcessedPath = new File(ucPreProcessedPath);
        this.classProcessedPath = new File(classProcessedPath);
    }

    public void doProcess() {

        /**
         * 也按照源代码那套处理
         * @date 2017/10/15
         */
        preprocessUCFiles(ucPath);

        preprocessJavaAndJsPFiles(classTxtPath);

    }

    private void preprocessJavaAndJsPFiles(File dirPath) {
        if (dirPath.isDirectory()) {
            for (File f : dirPath.listFiles()) {
                if (f.getName().endsWith("_jsp.txt") || f.getName().endsWith("_jspService.txt")) {
                    TextPreprocess textPreprocessor = new TextPreprocess(FileIOUtil.readFile(f.getPath()));
                    FileIOUtil.writeFile(textPreprocessor.doJspFileProcess(), classProcessedPath.getPath()+"/"+f.getName());
                } else if (f.getName().endsWith(".txt")) {
                    TextPreprocess textPreprocessor = new TextPreprocess(FileIOUtil.readFile(f.getPath()));
                    FileIOUtil.writeFile(textPreprocessor.doJavaFileProcess(), classProcessedPath.getPath()+"/"+f.getName());
                }
            }
        }
    }

    public void preprocessUCFiles(File ucDirPath) {
        if (ucDirPath.isDirectory()) {
            for (File f : ucDirPath.listFiles()) {
                if (f.getName().endsWith(".txt")) {
                    TextPreprocess textPreprocessor = new TextPreprocess(FileIOUtil.readFile(f.getPath()));
                    FileIOUtil.writeFile(textPreprocessor.doUCFileProcess(), ucPreProcessedPath.getPath()+"/"+f.getName());
                }
            }
        }
    }
}
