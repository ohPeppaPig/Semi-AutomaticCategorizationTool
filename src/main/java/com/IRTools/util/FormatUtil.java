package com.IRTools.util;

import com.IRTools.preprocess.CleanUp;
import com.IRTools.preprocess.Snowball;
import com.IRTools.preprocess.Stopwords;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.huaban.analysis.jieba.JiebaSegmenter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormatUtil {
    /**
     * 去除停用词
     * @param oldString：原中文文本
     * @return 去除停用词之后的中文文本
     * @throws IOException
     */
    private static String chineseStopWords = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\preprocess\\stopWords.txt";

    public static String ChineseSplitAndRemoveStopWords(String str) throws IOException {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<Term> segment = HanLP.segment(str);
        str = CleanUp.chineseClean(str);
        str = Snowball.stemming(str);
        List<String> strings = segmenter.sentenceProcess(str);
        str = String.join(" ", strings);
        str = Stopwords.remover(str,chineseStopWords);
        return str;
    }

    public static void main(String[] args) throws IOException {
        String str = "【43周需求问题】 25.环境：300，发送2部分物资，签收一部分，退回一部分，系统消息显示签收全部物资——需求明确，方燕玉26.环境：300，拒绝全部／部分物资后，卡片颜色显示-与需求不一致——需求明确，方燕玉【解决方案】会话框中的文案：称谓遵循会话系统，统一使用“你”“全部”指：一张卡片中，发送方发送的全部物资，比如发送了3支铅笔和5本便签本“部分”指：一张卡片中的一部分物资，退回了一部分，比如签收了2支铅笔，即签收了部分物资；退回了2本便签本，即退回了部分物资卡片数量：不同的物资库中的物资，会根据库的数量分成不同的卡片卡片颜色：卡片颜色根据卡片中物资是否处理完，显示不同的颜色没处理：蓝色处理一部分：灰蓝色（见视觉图）全部处理完：灰色 ";
        System.out.println(ChineseSplitAndRemoveStopWords(str));
        str = CleanUp.chineseClean(str);
        str = Snowball.stemming(str);
        List<Term> segment = HanLP.segment(str);
        List<String> cixing = Arrays.asList("x", "c", "u", "d","p", "t", "uj", "m", "f", "r");
        List<String> strings = new ArrayList<>();
        for (Term t :
                segment) {
            if (!cixing.contains(t.nature.toString())){
//                System.out.println(t.word);
                strings.add(t.word);
            }
        }
        str = String.join(" ",strings);
        str = Stopwords.remover(str,chineseStopWords);
        System.out.println(str);
//        System.out.println(segment);

    }
}
