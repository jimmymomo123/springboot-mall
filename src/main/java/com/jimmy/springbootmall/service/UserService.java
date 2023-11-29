package com.jimmy.springbootmall.service;

import com.jimmy.springbootmall.dto.UserRegisterRequest;
import com.jimmy.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);

}
