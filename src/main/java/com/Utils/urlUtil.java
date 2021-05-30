package com.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class urlUtil {

    public static List<String> urlExtract(String json){
        List<String> res = new ArrayList<>();

        JSONObject jsonObject = JSON.parseObject(json);

        String entityMap = jsonObject.getString("entityMap");
        if(entityMap.equals("{}")){
            return null;
        }
        //先将这条数据解析为JSONObject
        JSONObject outJson = JSONObject.parseObject(entityMap);
        //因为外部的JSON的key为三位数字的编号，我们需要得到编号，才能得到它对应的内部json
        Set<String> jsonSet = outJson.keySet();
        Iterator<String> iterator = jsonSet.iterator();
        while (iterator.hasNext()){
            //通过迭代器可以取到外部json的key
            String jsonKey = iterator.next();

            //取得内部json字符串
            String string = outJson.getString(jsonKey);
            //将内部json字符串解析为object对象
            JSONObject inJson = JSONObject.parseObject(string);
            if(inJson.getString("mutability").equals("MUTABLE")&&inJson.getString("type").equals("MEDIA")){
                // 第三层
                Set<String> inJsonSet = inJson.keySet();
                Iterator<String> inIterator = inJsonSet.iterator();
                while (inIterator.hasNext()){
                    String nextKey = inIterator.next();
                    if("data".equals(nextKey)){
                        String s = inJson.getString(nextKey);
                        JSONObject in2Json = JSONObject.parseObject(s);
                        String data = in2Json.getString("data");
                        JSONObject object = JSONObject.parseObject(data);
                        res.add(object.getString("src"));
                    }

                }
            }

        }
        return res;

    }

    public static String urlExtractByRegular(String content){
        String regex = "https://mkpub-release.menkor.com/affair/10000223/announcement/(?!(\\.jpg|\\.jpeg|\\.png)).+?(\\.jpg|\\.jpeg|\\.png)";
        Pattern p = Pattern.compile(regex);
        Matcher ma = p.matcher(content);
        List<String> res = new ArrayList<>();
        while(ma.find()) {
            res.add(ma.group());
        }
        return res.toString();
    }

    public static void main(String[] args) {

        String msg = "【衣食住行】25周交互优化";
        List<String> list = extractMessageByRegular(msg);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+"-->"+list.get(i));
        }
    }

    /**
     * 使用正则表达式提取中括号中的内容
     * @param msg
     * @return
     */
    public static List<String> extractMessageByRegular(String msg){

        List<String> list=new ArrayList<String>();
        Pattern p = Pattern.compile("(\\【[^\\]]*\\】)");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(1, m.group().length()-1));
        }
        return list;
    }



}
