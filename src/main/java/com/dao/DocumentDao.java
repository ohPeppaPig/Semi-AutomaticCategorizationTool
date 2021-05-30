package com.dao;

import com.VO.DetailedDocumentVo;
import com.VO.RemarkedDocumentVo;
import com.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface DocumentDao {

    @Select("SELECT count(*) from `announcement`")
    int findAll();

    /**
     * 查找所有文档的id title content
     * @return Document
     */
    @Select("SELECT id,title,content from `announcement`")
    List<Document> findAllText();


    /**
     * 查找所有文档的id title content
     * @return Document
     */
    @Select("SELECT id,title,content from `announcement_text`")
    List<Document> findAllTextDetail();


    @Select("SELECT id,title,content from `announcement_text` where id = #{id}")
    Document findDocById(int id);

    @Select("SELECT id,title,content,cls from `announcement_cls` where id = #{id}")
    ClassifiedDocument findDocAndClsById(int id);




    /**
     * 插入新数据库的 详细text
     * @param
     */
    @Insert("insert into announcement_text       "
            + "(id,title,content)    "
            + "values                   "
            + "(#{id}, #{title}, #{content}) ")
    int insertDocumentText(@Param("id") int id,
                          @Param("title") String title,
                          @Param("content") String content);


    @Select("SELECT id,title,content from `announcement` WHERE content LIKE '%src%'")
    List<Document> findAllTextSrc();


    /**
     * 插入一级分类
     */
    @Insert("INSERT INTO announcement_category (id,title,content," +
            "category_level_one_id) VALUES (#{id},#{title}," +
            "#{content},#{categoryLevelOneId})")
    void insertDocumentCategoryOne(@Param("id") int id,
                                   @Param("title") String title,
                                   @Param("content") String content,
                                   @Param("categoryLevelOneId") int categoryLevelOneId);


    /**
     * 查找所有的分类文档信息
     * @return
     */
    @Select("select id,title,content,category_level_one_id,category_level_two_id from announcement_category where id > 100")
    List<DocumentCategory> findAllDocumentCategory();

    /**
     * 查找所有的分类文档信息
     * @return
     */
    @Select("select id,title,content,category_level_one_id,category_level_two_id from announcement_category where id < 1000")
    List<DocumentCategory> findAllgeverment();


    /**
     * 更新写入 二级分类
     */
    @Update("update announcement_category set category_level_two_id=#{twoId} where id = #{id}")
    void updateDocumentCategoryTwo(@Param("twoId") int twoId,@Param("id") int id);

    /**
     * 按照一级标题查询文档
     * @param id
     * @return
     */
    @Select("select id,title,content,category_level_one_id,category_level_two_id " +
            "from announcement_category where category_level_one_id = #{id}")
    List<DocumentCategory> findDocumentsByOneId(@Param("id") int id);

    /**
     * 按照二级标题查询文档
     * @param id
     * @return
     */
    @Select("select id,title,content,category_level_one_id,category_level_two_id,comment from announcement_category where category_level_two_id = #{id}")
    List<DocumentCategory> findDocumentsByTwoId(@Param("id") int id);

    /**
     * 按照id 查找文档
     * @param id
     * @return
     */
    @Select("select id,title,content,category_level_one_id,category_level_two_id from announcement_category where id = #{id}")
    DocumentCategory findDocumentById(@Param("id") int id);


    /**
     * 查询标题名称数量
     * @param name
     * @return
     */
    @Select("SELECT count(*) from announcement_category WHERE title LIKE '%${name}%' ")
    int findTitleNums(@Param("name") String name);


    /**
     * 文档备注信息
     * @param id
     * @param comment
     */
    @Update("update announcement_category set comment=#{comment} where id = #{id}")
    void updateDocumentComment(@Param("id") int id,@Param("comment")String comment);

    @Select("select comment from announcement_category where id = #{id}")
    String findDocumentComment(@Param("id")int id);


    void insertDocumentComment(@Param("id") int id,@Param("comment")String comment);

    /**
     * 查找备注过的文档
     * @return
     */
    @Select("select id,title,category_level_one_id,category_level_two_id,comment from announcement_category where comment is not null and id>100")
    List<RemarkedDoc> findRemarkedDocumentVo();

    /**
     * 按照id查找备注文档
     * @return
     */
    @Select("select id,title,category_level_one_id,category_level_two_id,comment from announcement_category where id = #{id}")
    RemarkedDoc findRemarkedDocumentVoById(@Param("id")int id);

    @Select("select id,title,category_level_one_id,category_level_two_id,comment from announcement_category where comment is not null and id>100")
    List<RemarkedDoc> findRemarkedDocumentVo2();

    @Update("update announcement_category set comment=null where id = #{id}")
    void delComment(int id);


    @Select("select title,content,url,comment from announcement_category where id = #{id}")
    DocDetail findAllContents(@Param("id")int id);

    @Select("select count(*) from announcement_category where id>100 and comment is not null")
    int findCommentNums();

    @Select("select count(*) from announcement_category where id<100 and comment is not null")
    int findCommentNums2();

    @Update("update announcement_category set category_level_two_id=#{twoId} " +
            "where id = #{id}")
    void updateCategoryTwoTitle(@Param("id")int id,@Param("twoId") int twoId);

}
