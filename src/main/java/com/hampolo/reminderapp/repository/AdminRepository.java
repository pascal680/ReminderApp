package com.hampolo.reminderapp.repository;

import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {

  public Admin findByEmailIgnoreCase(String email);

  public Admin findByEmailAndPasswordIgnoreCase(String email, String password);

}
