package com.hampolo.reminderapp.repository;

import com.hampolo.reminderapp.model.Reminder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<Reminder,String> {

}
