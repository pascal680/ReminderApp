package com.hampolo.reminderapp.dto;

import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.ReminderDetails;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderAddDto {

  private String userId;

  private String reminderTitle;

  private LocalDateTime reminderDate;

  private ReminderDetails reminderDetails;

  private Category category;
}
