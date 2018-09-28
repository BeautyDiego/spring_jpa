package com.bdmc.myjpa.controller;

import java.util.List;

import com.bdmc.myjpa.entity.*;
import com.bdmc.myjpa.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class JPAController {
    @Autowired
    private UserService _userSvs;

    /**
     * 数据新增或更新，save方法可以执行添加也可以执行更新，如果需要执行持久化的实体存在主键值则更新数据，如果不存在则添加数据。
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public User save(User user) {
        return _userSvs.save(user);
    }

    /** * 查询用户信息 * */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> list() {
        return _userSvs.findAll();
    }

        /** * 查询用户信息 * */
        @RequestMapping(value = "/find", method = RequestMethod.GET)
        public User find(User user) {

            ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name", match -> match.startsWith())//模糊查询匹配开头，即{username}%
            .withMatcher("address" ,match -> match.contains())//全部模糊查询，即%{address}%
            .withIgnorePaths("password");//忽略字段，即不管password是什么值都不加入查询条件
            Example<User> example = Example.of(user ,matcher);

            return _userSvs.findOne(example);
        }

    /**
     * 删除用户信息，删除信息后返回剩余信息
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public List<User> delete(Long id) {
        _userSvs.deleteById(id);
        return _userSvs.findAll();
    }

}
