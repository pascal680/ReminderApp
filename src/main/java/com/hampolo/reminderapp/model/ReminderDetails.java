package com.hampolo.reminderapp.model;

import com.hampolo.reminderapp.model.enums.PriorityLevel;
import lombok.Data;

@Data
public class ReminderDetails {

  private String description;
  private PriorityLevel priority;

  public ReminderDetails(String description, PriorityLevel priority) {
    this.description = description;
    this.priority = priority;
  }
}
