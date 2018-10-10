package com.bdmc.myjpa.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.bdmc.myjpa.entity.User;
import com.bdmc.myjpa.jpa.UserJPA;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private UserJPA userjpa;

    @Override
    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        javax.persistence.Query query = em.createNativeQuery("select a.* from t_user a",User.class);
        //执行查询，返回的是String类型的集合，因为name这个属性是String类型
        List<User>  resultList = query.getResultList();
        System.out.println(resultList);
      // return userjpa.findAll();
       return resultList; 
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