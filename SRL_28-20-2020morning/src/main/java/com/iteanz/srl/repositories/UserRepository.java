package com.iteanz.srl.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iteanz.srl.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> { User
  findByName(String name); 

}
 
