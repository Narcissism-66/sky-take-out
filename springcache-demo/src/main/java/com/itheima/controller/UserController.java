package com.itheima.controller;

import com.itheima.entity.User;
import com.itheima.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    @CachePut(cacheNames = "userCache",key = "#user.id")//生成的key为userCache::id（#user.id是动态的值,从传入的参数里面获取的）
//    @CachePut(cacheNames = "userCache",key = "#result.id")//从这个方法的返回值里面获取id
//    @CachePut(cacheNames = "userCache",key = "#p0.id")//从传入的第一个参数里面获取id(p0与a0效果一样)
    public User save(@RequestBody User user){
        userMapper.insert(user);
        return user;
    }


    @DeleteMapping
    @CacheEvict(cacheNames = "userCache",key = "#id")//删除一个缓存
    public void deleteById(Long id){
        userMapper.deleteById(id);
    }

	@DeleteMapping("/delAll")
    @CacheEvict(cacheNames = "userCache",allEntries = true)//删除所有缓存
    public void deleteAll(){
        userMapper.deleteAll();
    }

    @GetMapping
    @Cacheable(cacheNames = "userCache",key = "#id")//先检查缓存里面是否有，如果没有才调用下面的方法，把返回值存起来
    public User getById(Long id){
        User user = userMapper.getById(id);
        return user;
    }

}
