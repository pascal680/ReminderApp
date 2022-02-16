package com.hampolo.reminderapp.repository;

import com.hampolo.reminderapp.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
