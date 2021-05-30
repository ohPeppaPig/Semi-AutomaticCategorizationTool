package com.IRTools.util;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;

import java.io.File;
import java.io.IOException;

public class TextClassificationUtil {
    public static final String CORPUS_FOLDER = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\classification02";
    /**
     * 模型保存路径
     */
//    public static final String MODEL_PATH = "data/test/classification-model.ser";

    //写个单例
    public static IClassifier classifier;

    static {
        try {
            classifier = new NaiveBayesClassifier(trainOrLoadModel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException
    {

        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());
    }

//    private static void predict(IClassifier classifier, String text)
////    {
////        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
////    }

    public static String predict(String text) throws IOException {
        return classifier.classify(text);
    }



    private static NaiveBayesModel trainOrLoadModel() throws IOException
    {
//        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
//        if (model != null) return model;

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory())
        {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                    "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        NaiveBayesModel model = (NaiveBayesModel) classifier.getModel();
//        IOUtil.saveObjectTo(model, MODEL_PATH);
        return model;
    }
}
