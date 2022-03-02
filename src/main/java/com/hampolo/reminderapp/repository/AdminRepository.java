package com.hampolo.reminderapp.repository;

import com.hampolo.reminderapp.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {

}
