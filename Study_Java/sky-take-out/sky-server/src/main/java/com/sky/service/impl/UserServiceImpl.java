package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
 * @Auther:fz
 * @Date:2025/7/30
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    WeChatProperties weChatProperties;
    @Autowired
    UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

        String openid=getOpenid(userLoginDTO.getCode());

        if (openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user=userMapper.getByOpenid(openid);

        if (user==null){
            user=User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    /**
     * 获取用户的openid
     * @param code
     * @return
     */
    private String getOpenid(String code){
        Map<String,String> map=new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json=HttpClientUtil.doGet(WX_LOGIN,map);

        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }
}
