package com.hampolo.reminderapp.repository;

import com.hampolo.reminderapp.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

  Optional<User> findByEmailIgnoreCase(String email);

  Optional<User> findByEmailIgnoreCaseAndPassword(String email, String password);

}
