package com.hampolo.reminderapp.model;

import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Reminders")
@Data
public class Reminder {

  @Id
  private String id;

  private String reminderTitle;

  private LocalDateTime reminderDate;

  private LocalDateTime reminderCreationDate;

  private ReminderDetails reminderDetails;

  private boolean isAllDay;

  private boolean isCompleted;


  @DBRef
  private Category reminderCategory;

  public Reminder(){
    this.reminderCreationDate = LocalDateTime.now();
  }

  public Reminder(String reminderTitle, LocalDateTime reminderDate, boolean isAllDay) {
    this.reminderTitle = reminderTitle;
    this.reminderDate = reminderDate;
    this.isAllDay = isAllDay;
    this.reminderCreationDate = LocalDateTime.now();
  }

  public Reminder(String reminderTitle, LocalDateTime reminderDate,
      ReminderDetails reminderDetails, Category reminderCategory, boolean isAllDay) {
    this.reminderTitle = reminderTitle;
    this.reminderDate = reminderDate;
    this.reminderDetails = reminderDetails;
    this.reminderCategory = reminderCategory;
    this.isAllDay = isAllDay;
    this.reminderCreationDate = LocalDateTime.now();
  }
}
