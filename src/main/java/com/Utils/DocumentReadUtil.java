//package com.Utils;
//
//import com.spire.doc.Document;
//import com.spire.pdf.PdfDocument;
//import com.spire.pdf.PdfPageBase;
//
//public class SpireReadUtil {
//    /**
//     * 读取Doc文件
//     * @param path
//     * @return
//     */
//    public static String getContentFromDoc(String path){
//        //加载Word文档
//        Document doc = new Document();
//        doc.loadFromFile(path);
//
//        //获取文档中的文本保存为String
//        return doc.getText();
//    }
//
//    /**
//     * 读取 pdf文件内容
//     * @param path
//     * @return
//     */
//    public static String getContentFromPdf(String path){
//        PdfDocument doc = new PdfDocument();
//        //加载PDF文件
//        doc.loadFromFile(path);
//
//        //创建StringBuilder实例
//        StringBuilder sb = new StringBuilder();
//
//        PdfPageBase page;
//        //遍历PDF页面，获取每个页面的文本并添加到StringBuilder对象
//        for(int i= 0;i<doc.getPages().getCount();i++){
//            page = doc.getPages().get(i);
//            sb.append(page.extractText(true));
//        }
//        return sb.toString();
//    }
//}
