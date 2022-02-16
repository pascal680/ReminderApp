package com.hampolo.reminderapp.model;

import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("RemindersDetails")
@Data
public class ReminderDetails {

  @Id
  private String id;

  private String description;
  private PriorityLevel priority;

  public ReminderDetails(String description, PriorityLevel priority) {
    this.description = description;
    this.priority = priority;
  }
}
