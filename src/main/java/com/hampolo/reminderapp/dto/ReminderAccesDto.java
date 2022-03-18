package com.hampolo.reminderapp.dto;

import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.ReminderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderAccesDto {

  private String id;

  private String reminderTitle;

  private Long reminderDate;

  private Long reminderCreationDate;

  private ReminderDetails reminderDetails;

  private Category reminderCategory;

}
