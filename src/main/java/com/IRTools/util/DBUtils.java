package com.IRTools.util;

import com.IRTools.preprocess.DataPreprecess;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBUtils {
    public static final String URL = "jdbc:mysql://localhost:3306/simu_order_test";
    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static String path = "C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\issue";

    static {

    }
    public static void main(String[] args) throws Exception {
        DataPreprecess preprecess = new DataPreprecess();
        preprecess.deleteFileInDir("C:\\Users\\南大宋仲基\\Desktop\\论文 (2)\\comnju\\src\\main\\java\\issue");
        textExtract();

    }

    public static List<String> blankText() throws Exception {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        String sql = "SELECT title,content FROM announcement";
        ResultSet rs = stmt.executeQuery(sql);

        // 记录blankText文本标题
        List<String> res = new ArrayList<>();
        //如果有数据，rs.next()返回true
        while (rs.next()) {
            String title = rs.getString("title");
            JSONObject jsonObject = JSON.parseObject(rs.getString("content"));

            JSONArray blocks = JSONArray.parseArray(jsonObject.getString("blocks"));
            StringBuilder sb = new StringBuilder();
            if(blocks.size()<=1){
                res.add(title);
            }
        }
        for (String text :
                res) {
            System.out.println(text);
        }
        System.out.println(res.size());
        System.out.println(res.size());
        stmt.close();
        conn.close();
        return res;
    }

    private static void textExtract() throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        String sql = "SELECT title,content FROM announcement";
        ResultSet rs = stmt.executeQuery(sql);
        //如果有数据，rs.next()返回true
        while (rs.next()) {
            String title = rs.getString("title");
            JSONObject jsonObject = JSON.parseObject(rs.getString("content"));

            JSONArray blocks = JSONArray.parseArray(jsonObject.getString("blocks"));
            if(blocks.size() <= 1){
                continue;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < blocks.size(); i++) {
                JSONObject json = blocks.getJSONObject(i);
                String pic = json.getString("text");
                sb.append(pic);
            }
            System.out.println(sb.toString());
            FileIOUtil.writeFile(sb.toString(), path + "/" + title + ".txt");

        }
    }

    // 找出所有的标题关键词
    public static Set<String> findKeyWords() throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        String sql = "SELECT title FROM announcement";
        ResultSet rs = stmt.executeQuery(sql);

        List<String> list = new ArrayList<>();

        while (rs.next()){
            String title = rs.getString("title");
            int i = title.indexOf("【");
            int j = title.indexOf("】");
            if(i == j){
                continue;
            }
            String key = title.substring(i, j + 1);
            list.add(key);
        }
        System.out.println(list.size());
        Set<String> set = new HashSet<>(list);
        return set;
    }

    // 美化 输出json
    public static String prettyJson(String search_id) throws Exception {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM `announcement_history` WHERE id = "+search_id;
        ResultSet rs = stmt.executeQuery(sql);

//        List<historyObject> res = new ArrayList<>();
//        while (rs.next()){
//            String id = rs.getString("id");
//            String title = rs.getString("title");
//            String increment = rs.getString("increment");
//            String decrement = rs.getString("decrement");
//            String version = rs.getString("version");
//            historyObject object = new historyObject();
//            object.setId(Integer.valueOf(id));
//            object.setTitle(title);
//            object.setIncrement(JsonUtil.jsonFormat(increment));
//            object.setDecrement(JsonUtil.jsonFormat(decrement));
//            object.setVersion(Integer.valueOf(version));
//            res.add(object);
        StringBuilder sb = new StringBuilder();
        while (rs.next()){
            String increment = JsonUtil.jsonFormat(rs.getString("increment"));

            String decrement = JsonUtil.jsonFormat(rs.getString("decrement"));
            sb.append("increment:");
            sb.append(increment);
            sb.append("\n");
            sb.append("decrement");
            sb.append(decrement);

        }
        return sb.toString();
    }
}
