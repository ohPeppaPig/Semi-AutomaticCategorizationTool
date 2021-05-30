package com.dao;

import com.entity.AdminChartDisplay;
import com.entity.CategoryLink;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TitleClusterDao {
    @Insert("insert into category "
            + "(sname,pid)    "
            + "values                   "
            + "(#{sname}, #{pid}) ")
    /**
     * 插入分类
     */
    void insertCategoryLevel(@Param("sname") String sname, @Param("pid") int pid);

    /**
     * 找出分类名称的sid
     * @param name
     * @return
     */
    @Select("SELECT sid FROM category WHERE sname = #{sname}")
    int findCategorySidBySname(@Param("sname") String name);

    /**
     * 根据父级分类id 查询子级分类id
     * @param pid
     * @return
     */
    @Select("select sid,sname,pid from category where pid = #{pid}")
    List<CategoryLink> findCategoryByPid(@Param("pid") int pid);


    @Select("select sname from category where sid = #{sid}")
    String findCategoryNameById(@Param("sid") int sid);


    @Select("SELECT c.sid as sid,c.sname as name,count(*) as value from (SELECT sid,sname from category WHERE pid = #{id}) as c LEFT JOIN announcement_category on c.sid = announcement_category.category_level_one_id GROUP BY c.sid")
    List<AdminChartDisplay> findChartNums(@Param("id") int id);

    @Update("update category set sname = #{name} where sid = #{id}")
    void updateTitleName(@Param("id")int id,@Param("name")String name);

    @Update("update category set pid = #{targetId} where sid = #{id}")
    int updateTitleCluster(@Param("id")int id,@Param("targetId")int targetId);

}
