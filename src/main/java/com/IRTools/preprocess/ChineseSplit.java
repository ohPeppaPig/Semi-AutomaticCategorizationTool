package com.IRTools.preprocess;



import com.IRTools.util.FileIOUtil;

import java.io.File;

/**
 * 中文文本进行分词
 */
public class ChineseSplit {
    private File origin;
    private File target;

    public ChineseSplit(String origin, String target) {
        this.origin = new File(origin);
        this.target = new File(target);
    }

    public void doProcess() {

        preprocessFiles(origin);

    }

    private void preprocessFiles(File origin) {

        if (origin.isDirectory()) {
            for (File f : origin.listFiles()) {
                if (f.getName().endsWith(".txt")) {
                    String str = FileIOUtil.readFile(f.getPath());
//                    List<String> strings = segmenter.sentenceProcess(str);
//                    String seg = String.join(" ", strings);

                    TextPreprocess textPreprocessor = new TextPreprocess(str);
                    FileIOUtil.writeFile(textPreprocessor.doChineseFileProcess(), target.getPath()+"/"+f.getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        String origin = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\data";
        String target = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\result";
        ChineseSplit cs = new ChineseSplit(origin,target);
        cs.doProcess();
    }

}
