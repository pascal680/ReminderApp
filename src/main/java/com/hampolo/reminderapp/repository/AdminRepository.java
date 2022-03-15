package com.hampolo.reminderapp.repository;

import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {

  public Optional<Admin> findByEmailIgnoreCase(String email);

  public Optional<Admin> findByEmailIgnoreCaseAndPassword(String email, String password);

}
