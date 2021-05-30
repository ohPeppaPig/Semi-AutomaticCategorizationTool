package com.HanLpTools;

import com.entity.Document;
import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * HanLp分本分类推荐
 */

public class classifictionUtil {
    /**
     * 搜狗文本分类语料库5个类目，每个类目下1000篇文章，共计5000篇文章
     */
//    public static final String CORPUS_FOLDER = TestUtility.ensureTestData("dataMini", "http://file.hankcs.com/corpus/sogou-text-classification-corpus-mini.zip");
    public static final String CORPUS_FOLDER = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\simu_data";

    /**
     * 模型保存路径
     */
    public static final String MODEL_PATH = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\data\\testclassification-model.ser";



    private static  NaiveBayesModel trainOrLoadModel() throws IOException
    {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(MODEL_PATH);
        if (model != null) return model;

        File corpusFolder = new File(CORPUS_FOLDER);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory())
        {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                    "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
            System.exit(1);
        }

        IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
        classifier.train(CORPUS_FOLDER);                     // 训练后的模型支持持久化，下次就不必训练了
        NaiveBayesModel naiveBayesModel = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(naiveBayesModel, MODEL_PATH);
        return naiveBayesModel;
    }

    public static String predict(String text) throws IOException {
        IClassifier classifier = new NaiveBayesClassifier(trainOrLoadModel());
        String classify = classifier.classify(text);
        return classify;

    }
}
