package com.bdmc.myjpa.services;
import java.util.*;


import org.springframework.data.domain.Example;


public interface IBaseService<T> {
 
    public List<T> findAll();

    public T findOne(Example<T> example);

    //public boolean Add(T entity);

    public T findOneById(long id);

    public T save(T user);

    public void deleteById(long id);
}
