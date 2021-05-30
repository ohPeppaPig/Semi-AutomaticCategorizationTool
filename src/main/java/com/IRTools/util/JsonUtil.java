package com.IRTools.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;

public class JsonUtil {
    public static String jsonFormat(String jsonString) {
        JSONObject object = JSONObject.parseObject(jsonString);
        jsonString = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        return jsonString;

    }

    public static void main(String[] args) {
        String s1 = "{\"data\":{},\"depth\":2,\"entityRanges\":[],\"inlineStyleRanges\":[{\"length\":14,\"offset\":0,\"style\":\"c_0xFF2E54EB\"}],\"key\":\"3s77q\",\"text\":\"价格：新增字段，取自上架价格\",\"type\":\"unordered-list-item\"}";
        JSONObject jsonObject1 = JSONObject.parseObject(s1);
        System.out.println(jsonObject1);
        String s = "{\"delete\":[143,128,112,102],\"insert\":[{\"content\":[{\"data\":{},\"depth\":2,\"entityRanges\":[],\"inlineStyleRanges\":[{\"offset\":0,\"length\":14,\"style\":\"c_0xFF2E54EB\"}],\"key\":\"3s77q\",\"text\":\"价格：新增字段，取自上架价格\",\"type\":\"unordered-list-item\"}],\"position\":237},{\"content\":[{\"data\":{},\"depth\":0,\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":\"3dn94\"}],\"inlineStyleRanges\":[],\"key\":\"as65a\",\"text\":\" \",\"type\":\"atomic\"}],\"position\":167}],\"replace\":[{\"content\":{\"data\":{},\"depth\":0,\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":\"4obmj\"}],\"inlineStyleRanges\":[],\"key\":\"8a2u6\",\"text\":\" \",\"type\":\"atomic\"},\"position\":239},{\"content\":{\"data\":{},\"depth\":0,\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":\"6jqtg\"}],\"inlineStyleRanges\":[],\"key\":\"5gem0\",\"text\":\" \",\"type\":\"atomic\"},\"position\":201},{\"content\":{\"data\":{},\"depth\":0,\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":\"an763\"}],\"inlineStyleRanges\":[],\"key\":\"eells\",\"text\":\" \",\"type\":\"atomic\"},\"position\":199},{\"content\":{\"data\":{},\"depth\":0,\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":\"fpgs2\"}],\"inlineStyleRanges\":[],\"key\":\"bdlh3\",\"text\":\" \",\"type\":\"atomic\"},\"position\":167},{\"content\":{\"data\":{},\"depth\":1,\"entityRanges\":[],\"inlineStyleRanges\":[{\"offset\":0,\"length\":43,\"style\":\"c_0xFF4A4A4A\"}],\"key\":\"eig2f\",\"text\":\"对选中的商品进行编辑操作，保存修改时提示：保存修改后，将会覆盖商品之前的上架配置信息！\",\"type\":\"unordered-list-item\"},\"position\":166},{\"content\":{\"data\":{},\"depth\":2,\"entityRanges\":[],\"inlineStyleRanges\":[{\"offset\":0,\"length\":63,\"style\":\"c_0xFF4A4A4A\"}],\"key\":\"8filc\",\"text\":\"对选中的商品进行上架操作，若已经上架，则上架信息覆盖之前的上架信息，并且提示：本次上架含有已上架商品，上架后将会覆盖之前配置！\",\"type\":\"unordered-list-item\"},\"position\":158},{\"content\":{\"data\":{},\"depth\":2,\"entityRanges\":[],\"inlineStyleRanges\":[{\"offset\":0,\"length\":45,\"style\":\"c_0xFF4A4A4A\"}],\"key\":\"d8e18\",\"text\":\"当某时间段的上架数量没有填写时，点击确认上架，提示：XX时间段未配置上架数量，请进行配置；\",\"type\":\"unordered-list-item\"},\"position\":157},{\"content\":{\"data\":{},\"depth\":1,\"entityRanges\":[],\"inlineStyleRanges\":[],\"key\":\"fh5va\",\"text\":\"步骤三：配置定价及上架数量\",\"type\":\"unordered-list-item\"},\"position\":140},{\"content\":{\"data\":{},\"depth\":0,\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":\"41qtp\"}],\"inlineStyleRanges\":[],\"key\":\"1vos6\",\"text\":\" \",\"type\":\"atomic\"},\"position\":122},{\"content\":{\"data\":{},\"depth\":3,\"entityRanges\":[],\"inlineStyleRanges\":[],\"key\":\"7h0k1\",\"text\":\"未搜索到商品时，提示：未搜索到该商品，点击立即创建；\",\"type\":\"unordered-list-item\"},\"position\":101},{\"content\":{\"data\":{},\"depth\":2,\"entityRanges\":[],\"inlineStyleRanges\":[],\"key\":\"9p749\",\"text\":\"搜索上架商品：套餐、单品；\",\"type\":\"unordered-list-item\"},\"position\":100},{\"content\":{\"data\":{},\"depth\":0,\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":\"40fa2\"}],\"inlineStyleRanges\":[],\"key\":\"9ih4u\",\"text\":\" \",\"type\":\"atomic\"},\"position\":90}]}";
        JSONObject jsonObject = JSONObject.parseObject(s);
        String replace = jsonObject.getString("replace");
        System.out.println(replace);
//        JSONArray blocks = JSONObject.parseArray(delete);
//        for (int i = 0; i < blocks.size(); i++) {
//            JSONObject json = blocks.getJSONObject(i);
//
//            String content = json.getString("content");
//            System.out.println(content);
//            JSONArray jsonArray = JSONObject.parseArray(content);
//            JSONObject object = jsonArray.getJSONObject(0);
//            System.out.println(object.getString("key"));
//            System.out.println(object.getString("text"));
//
//            System.out.println(json.getString("position"));
//
//
//        }
        JSONArray blocks = JSONObject.parseArray(replace);
        for (int i = 0; i < blocks.size(); i++) {
            JSONObject json = blocks.getJSONObject(i);
            String content = json.getString("content");
            JSONObject parseObject = JSONObject.parseObject(content);
            System.out.println(parseObject.getString("key"));
            System.out.println(parseObject.getString("text"));
        }

    }

    public static void del(HashMap<String,String> val){

    }
}
