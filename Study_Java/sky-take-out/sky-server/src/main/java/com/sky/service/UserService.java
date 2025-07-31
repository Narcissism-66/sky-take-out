package com.sky.service;

/*
 * @Auther:fz
 * @Date:2025/7/30
 * @Description:
 */

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
