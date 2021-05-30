package com.OCRTools;

import com.baidu.aip.ocr.AipOcr;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Tess4J测试类
 */
public class OCRService {

    //设置APPID/AK/SK
    public static final String APP_ID = "23780217";
    public static final String API_KEY = "mnDewF4SyGTDaMTAM9DyuDPe";
    public static final String SECRET_KEY = "nPgPFCPmjAiUyFeaNTz6GqCdKRp9UNhq";

    /**
     * 通过图片url获取解析的文字
     * @param imgUrl
     * @return
     */
    public static String getImgContent(String imgUrl){
        String text = "";
        try {
            AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);

            JSONObject resp = client.basicGeneralUrl(imgUrl, new HashMap<String, String>());
            System.out.println(resp);
            if(resp==null || resp.getJSONArray("words_result")==null){
                return null;
            }
            JSONArray jsonArray = resp.getJSONArray("words_result");
            if(jsonArray!=null&&jsonArray.length()>0){
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    if(StringUtils.isBlank(item.getString("words"))){
                        continue;
                    }
                    String words = item.getString("words");
                    text += words;
                }
            }
        } catch (Exception e) {
//            logger.info("使用百度OCR接口发生异常【{}】",e);
            return null;
        }
        return text;
    }


    public static void main(String[] args) {
        String url = "https://mkpub-release.menkor.com/affair/10000223/announcement/vu3SC6UFCD/1542107511497.png";
        String imgContent = getImgContent(url);
        System.out.println(imgContent);
    }

}


