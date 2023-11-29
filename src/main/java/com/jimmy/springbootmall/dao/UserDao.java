package com.jimmy.springbootmall.dao;

import com.jimmy.springbootmall.dto.UserRegisterRequest;
import com.jimmy.springbootmall.model.User;

public interface UserDao {

    User getUserByEmail(String email);
    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
