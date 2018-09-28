package com.bdmc.myjpa.services;
import java.util.*;

import com.bdmc.myjpa.entity.User;

import org.springframework.data.domain.Example;


public interface IUserService {
 
    public List<User> findAll();

    public User findOne(Example<User> example);

    public User findOneById(long id);

    public User save(User user);

    public void deleteById(long id);
}
