package com.dao;

import com.entity.Document;
import com.entity.UserDocumentLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DocumentHistoryDao {

    /**
     * 按照文档id查找父类文档
     * @param id
     * @return
     */
    @Select("SELECT id,title,content from announcement WHERE id = (SELECT DISTINCT(parent_id) FROM " +
            "announcement_history WHERE announcement_id = #{id})")
    Document findParentDocumentByid(int id);

    /**
     * 按照文档id查找子类文档
     * @param id
     * @return
     */
    @Select("SELECT id,title,content from announcement WHERE id in (SELECT DISTINCT(announcement_id) FROM " +
            "announcement_history WHERE parent_id = #{id})")
    List<Document> findChildDocumentById(int id);


    /**
     * 查询文档与修改人对应关系
     * @return
     */
    @Select("SELECT announcement_id,modifier_user_id FROM announcement_history GROUP BY announcement_id,modifier_user_id")
    List<UserDocumentLink> findUserDocumentLinks();
}
