package com.IRTools.preprocess;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.nio.file.Paths;
import java.util.List;

public class TextPreprocess {
    private String str;
    private String stopwordsPath = "data/stopwords/stop-words_english_1_en.txt";
    private String jspStopwordsPath = "data/stopwords/stop-words_jsp.txt";

    // 去除中文词
//   private String chineseStopWords = "data/stopWords.txt";
   private String chineseStopWords = "E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\IRTools\\preprocess\\stopWords.txt";

    public TextPreprocess(String str) {
        this.str = str;
    }

    public TextPreprocess() {
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String doUCFileProcess() {
        str = CleanUp.chararctorClean(str);
        // here
//        str = CamelCase.split(str);
        str = CleanUp.lengthFilter(str, 3);
        str = CleanUp.tolowerCase(str);
        str = Snowball.stemming(str);
        str = Stopwords.remover(str, stopwordsPath);
        return str;
    }

    public String doJavaFileProcess() {
        str = CleanUp.chararctorClean(str);
        str = CamelCase.split(str);
        str = CleanUp.lengthFilter(str, 3);
        str = CleanUp.tolowerCase(str);
        str = Snowball.stemming(str);
        str = Stopwords.remover(str, stopwordsPath);
        return str;
    }

    public String doJspFileProcess() {
        str = CleanUp.chararctorClean(str);
        str = Stopwords.remover(str, jspStopwordsPath);
        str = CamelCase.split(str);
        str = CleanUp.lengthFilter(str, 3);
        str = CleanUp.tolowerCase(str);
        str = Snowball.stemming(str);
        str = Stopwords.remover(str, stopwordsPath);
        str = Stopwords.remover(str, jspStopwordsPath);
        return str;
    }

    public String doChineseFileProcess(){
        WordDictionary.getInstance().loadUserDict(Paths.get("E:\\gitt\\springboot_mybatis\\src\\main\\java\\com\\IRTools\\preprocess\\words")) ;
        JiebaSegmenter segmenter = new JiebaSegmenter();

        str = CleanUp.chineseClean(str);
        str = Snowball.stemming(str);
        List<String> strings = segmenter.sentenceProcess(str);
        str = String.join(" ", strings);
        str = Stopwords.remover(str,chineseStopWords);
        return str;
    }


}
