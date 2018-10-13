package com.bdmc.myjpa.jpa;
 
import com.bdmc.myjpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//import java.awt.print.Pageable;
import java.io.Serializable;
 
public interface UserJPA extends JpaRepository<User,Long>,JpaSpecificationExecutor<User>,Serializable {
   // public User FindByNameAndPassword(String name,String password,Pageable pageable);
}
