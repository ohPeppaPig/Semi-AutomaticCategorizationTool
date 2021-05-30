package com.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PretreatmentDao {

    @Insert("INSERT INTO reserved_word (reserved_word) VALUES (#{str})")
    void insertReversedWords(@Param("str") String str);
}
