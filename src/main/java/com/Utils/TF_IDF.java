package com.Utils;

import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TF_IDF {
    /**
          * 计算每个文档的tf值
          * @param wordAll
          * @return Map<String,Float> key是单词 value是tf值
          */
    public static Map<String,Float> tfCalculate(String wordAll){
    //存放（单词，单词数量）
        HashMap<String, Integer> dict = new HashMap<String, Integer>();
        //存放（单词，单词词频）
        HashMap<String, Float> tf = new HashMap<String, Float>();
        int wordCount=0;

        /**
             * 统计每个单词的数量，并存放到map中去
             * 便于以后计算每个单词的词频
             * 单词的tf=该单词出现的数量n/总的单词数wordCount
             */
        for(String word:wordAll.split(" ")){
            wordCount++;
            if(dict.containsKey(word)){
                dict.put(word,dict.get(word)+1);
            }else{
                dict.put(word, 1);
                }
            }

            for(Map.Entry<String, Integer> entry:dict.entrySet()){
                 float wordTf=(float)entry.getValue()/wordCount;
                tf.put(entry.getKey(), wordTf);
            }
            return tf;
    }

    /**
          * 
          * @param D 总文档数
          * @param doc_words 每个文档对应的分词
          * @param tf 计算好的tf,用这个作为基础计算tfidf
          * @return 单词对应的tfidf的值
          * @throws IOException 
          * @throws FileNotFoundException 
          */
    public static Map<String,Float> tfidfCalculate(int D, Map<String,String> doc_words,Map<String,Float> tf) throws FileNotFoundException, IOException {

        HashMap<String,Float> tfidf=new HashMap<String, Float>();
        // 遍历tf词库中的每个单词
        for(String key:tf.keySet()){
            int Dt=0;
          // 遍历每篇文档的切词内容
          for(Map.Entry<String, String> entry:doc_words.entrySet()){

            String[] words=entry.getValue().split(" ");

            List<String> wordlist = new ArrayList<String>(Arrays.asList(words));
            if(wordlist.contains(key)){
                 Dt++;
            }

          }
            float idfvalue=(float) Math.log((float) D /Dt);
             tfidf.put(key, idfvalue * tf.get(key));

        }
        return tfidf;
    }

    public static Map<String,Float> tfidfCalculate(int D, List<String> doc_words,Map<String,Float> tf) throws FileNotFoundException, IOException {

        HashMap<String,Float> tfidf=new HashMap<String, Float>();

        // 遍历tf词库中的每个单词
        for(String key:tf.keySet()){
            int Dt=0;
            // 遍历每篇文档的切词内容

            for (String word : doc_words) {
                String[] split = word.split(" ");
                List<String> wordlist = new ArrayList<String>(Arrays.asList(split));
                if(wordlist.contains(key)){
                    Dt++;
                }
            }
            float idfvalue=(float) Math.log((float) D /Dt);
            tfidf.put(key, idfvalue * tf.get(key));

        }
        return tfidf;
    }

    public static Map<String,Float> idfCalculate(int D, List<String> doc_words,List<String> tf) throws FileNotFoundException, IOException {

        HashMap<String,Float> tfidf=new HashMap<String, Float>();

        // 遍历tf词库中的每个单词
        for(String key:tf){
            int Dt=0;
            // 遍历每篇文档的切词内容

            for (String word : doc_words) {
                String[] split = word.split(" ");
                List<String> wordlist = new ArrayList<String>(Arrays.asList(split));
                if(wordlist.contains(key)){
                    Dt++;
                }
            }
            float idfvalue=(float) Math.log((float) D /Dt);
            tfidf.put(key, idfvalue);

        }
        return tfidf;
    }



    public static Map<String,Float> getWordsTfIDF(List<String> docs) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String doc:docs) {
            sb.append(doc).append(" ");
        }
        String wordsAll = sb.toString();
        Map<String, Float> TFMap = tfCalculate(wordsAll);
        return tfidfCalculate(docs.size(),docs,TFMap);

    }

//    public static Map<String,Float> getWordsIDF(List<String> docs) throws IOException {
//        List<String> arr = new ArrayList<>(1000);
//
//        for (String doc:docs) {
//
//        }
//        return tfidfCalculate(docs.size(),docs,TFMap);
//
//    }
public static void main(String[] args) {
    for (String algorithm : new String[]{"kmeans", "repeated bisection"})
    {
        System.out.printf("%s F1=%.2f\n", algorithm, ClusterAnalyzer.evaluate("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\dataMini", algorithm) * 100);
    }

}

}
