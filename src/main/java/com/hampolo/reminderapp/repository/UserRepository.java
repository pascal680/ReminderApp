package com.hampolo.reminderapp.repository;

import com.hampolo.reminderapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

  public User findByEmail(String email);

  public User findByEmailAndPasswordIgnoreCase(String email, String password);

}
