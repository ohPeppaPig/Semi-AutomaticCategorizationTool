package com.dao;

import com.entity.ClassifiedDocument;
import com.entity.Document;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassifictionDao {

    @Select("SELECT id,title,content FROM `announcement_text` WHERE title LIKE '%盟客网%'")
    List<Document> fuzzyQuery(String name);

    @Select("SELECT id,title,content FROM `announcement_text` WHERE title  not LIKE '%物资%' and title not like '%衣食住行%' and title not like '%成长伙伴%' and title not like '%思目论坛%' and title not like '%智慧城市%' and title not like '%超级账号%' and title not like '%产学研教%'")
    List<Document> predict();

    @Insert("insert into announcement_cls "
            + "(id,title,content,cls)    "
            + "values                   "
            + "(#{id}, #{title}, #{content}, #{cls}) ")
    void insertCls(@Param("id") int id, @Param("title") String title, @Param("content") String content, @Param("cls") String cls);

    @Select("SELECT id,title,content from announcement_cls WHERE cls = #{cls}")
    List<Document> findDocumentByCls(String cls);

    @Select("SELECT id,title,content,cls from announcement_cls")
    List<ClassifiedDocument> findAllCls();
}
