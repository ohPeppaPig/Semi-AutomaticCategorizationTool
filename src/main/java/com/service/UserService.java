package com.service;

import com.dao.DocumentDao;
import com.dao.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DocumentDao documentDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public int find(){
        return documentDao.findAll();
    }

}
