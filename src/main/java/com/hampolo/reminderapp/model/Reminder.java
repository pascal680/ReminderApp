package com.hampolo.reminderapp.model;

import java.time.LocalDate;
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
  private LocalDate reminderDate;
  private LocalDate reminderCreationDate;

  private ReminderDetails reminderDetails;

  @DBRef
  private Category reminderCategory;



  public Reminder(String reminderTitle, LocalDate reminderDate) {
    this.reminderTitle = reminderTitle;
    this.reminderDate = reminderDate;
    this.reminderCreationDate = LocalDate.now();
  }
}
