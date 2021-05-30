package com.controller;


import com.Utils.FileUtil;
import com.VO.*;
import com.entity.Document;
import com.entity.DocumentCategory;
import com.service.DocumentService;
import com.service.TitleClusterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/docs")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @Autowired
    TitleClusterService titleClusterService;


    /**
     * 查看一级标题下的二级菜单的关键词与关键句
     * @param oneId
     * @return
     */

    @RequestMapping(value="/secondaryMenu/{oneId}",method= RequestMethod.GET)
    public List<SecondaryMenuVo> secondaryMenu(@PathVariable("oneId") Integer oneId){
        // TODO: 2021/4/12  redis 加速访问
        return titleClusterService.secondaryMenu(oneId);
    }

    /**
     * 滑动条 二级文档簇 diff
     */
    @RequestMapping(value="/dynamicAdjustCluster/{oneId}/{n}",method= RequestMethod.GET)
    public DocumentClusterDiffVo dynamicAdjustCluster(@PathVariable("oneId")int oneId,
                                                      @PathVariable("n")int n){
        return documentService.dynamicAdjustCluster(oneId,n);

    }

    /**
     * 查看二级标题下的对应文档信息
     * @return
     */
    @RequestMapping(value="/findDocumentByTwoId/{twoId}",method= RequestMethod.GET)
    public List<DocumentCategory> findDocumentByTwoId(@PathVariable("twoId") Integer twoId){
       return documentService.findDocumentByTwoId(twoId);
    }


    /**
     * 查看具体文档信息 与 高相似度文档
     * @return
     */
    @RequestMapping(value="/findDocumentDetail/{id}",method= RequestMethod.GET)
    public DocumentDetail findDocumentDetail(@PathVariable("id") Integer id){
        // TODO: 2021/4/12 redis 加速
        return documentService.findDocumentDetail(id);
    }

    @RequestMapping(value="/findDocumentDetail",method= RequestMethod.GET)
    public void findDocument(){
        // TODO: 2021/4/12 redis 加速
        System.out.println(1);
    }



    /**
     * 更新文档的二级标题
     * @param ids
     * @param twoId
     */
    @RequestMapping(value = "/updateCategory/{ids}/{twoId}",method= RequestMethod.POST)
    public void updateCategory(@PathVariable("ids") List<Integer> ids,@PathVariable("twoId") Integer twoId){
        documentService.updateCategory(ids,twoId);
        System.out.println(ids.get(0));
        System.out.println(twoId);
    }

    @RequestMapping(value = "/moveMulti/{arr}/{twoId}",method= RequestMethod.POST)
    public void moveMulti(@PathVariable("arr") List<Integer> docs,@PathVariable("twoId") Integer twoId){
        System.out.println(docs.size());
    }





    /**
     * 上传接口
     * @param folder
     * @return
     */
    @RequestMapping(value = "/uploadFolder", method = RequestMethod.POST)
    public String uploadFolder(MultipartFile[] folder) {
        // TODO: 2021/4/13  考虑策略模式进行修改
        System.out.println("上传成功");
//        System.out.println(folder.length);

//        List<String> suffix = Arrays.asList(".txt",".pdf",".doc",".docx");
//        for (MultipartFile file:
//             folder) {
//            //获取上传文件名,包含后缀
//            String originalFilename = file.getOriginalFilename();
//            //获取后缀
//            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
//            if(!suffix.contains(substring)){
//                return "上传文件类型不符合规定，请检查！";
//            }
//            //保存的文件名
//            String dFileName = originalFilename.substring(0,originalFilename.lastIndexOf("."));
//            //保存路径
//            //springboot 默认情况下只能加载 resource文件夹下静态资源文件
//            String path = "D:/workspace/demo2/src/main/resources/static/image/";
//            //生成保存文件
//            File uploadFile = new File(path+dFileName);
//            System.out.println(uploadFile);
//            //将上传文件保存到路径
//            try {
//                file.transferTo(uploadFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        return "上传"+folder.length+"成功";
        return "上传成功";
//        FileUtil.saveMultiFile("D:/upload", folder);
//        return "ok";
    }

    /**
     * 增加批注
     * @param id
     */
    @RequestMapping(value="/insertComment/{id}/{comment}",method= RequestMethod.POST)
    public void insertComment(@PathVariable("id")Integer id,@PathVariable("comment")String comment){
        documentService.updateDocumentComment(id,comment);
    }

    @RequestMapping(value="/findComment/{id}",method= RequestMethod.GET)
    public String findComment(@PathVariable("id")Integer id){
        return documentService.findDocumentComment(id);
    }

    @RequestMapping(value="/findRemarkedDocument",method= RequestMethod.GET)
    public List<RemarkedDocumentVo> findRemarkedDocument(){
        return documentService.findRemarkedDocs();
    }

    @RequestMapping(value="/delComment/{id}",method= RequestMethod.GET)
    public void delComment(@PathVariable("id")Integer id){
        documentService.delComment(id);
    }
    /**
     * 查找文章的详细内容 与 url
     */
    @RequestMapping(value="/findAllContents/{id}",method= RequestMethod.GET)
    public DetailedDocumentVo findAllContents(@PathVariable("id")Integer id){
        return documentService.findAllContents(id);
    }

    public static void main(String[] args) {
        String originalFilename = "12345.txt";
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(substring);
    }

}
