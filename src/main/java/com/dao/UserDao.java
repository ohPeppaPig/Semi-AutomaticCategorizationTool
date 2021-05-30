package com.dao;

import com.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    public List<User> findAll();



    public int commentReply();

}
