package com.bdmc.myjpa.services;

import java.util.List;
import com.bdmc.myjpa.entity.User;
import com.bdmc.myjpa.jpa.UserJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    @Autowired
    private UserJPA userjpa;

    @Override
    public List<User> findAll() {
        return userjpa.findAll();
    }

    @Override
    public User findOne(Example<User> example) {
        return userjpa.findOne(example).orElse(null);
	}

	@Override
	public User findOneById(long id) {
        
		return userjpa.findById(id).orElse(null);
	}

    @Override
    public User save(User user) {
        return userjpa.save(user);
    }

    @Override
    public void deleteById(long id){
        userjpa.deleteById(id);
    }

}