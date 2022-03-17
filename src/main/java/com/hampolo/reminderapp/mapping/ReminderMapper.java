package com.hampolo.reminderapp.mapping;

import com.hampolo.reminderapp.dto.ReminderAddDto;
import com.hampolo.reminderapp.model.Reminder;

public abstract class ReminderMapper {

  public static Reminder toEntity(ReminderAddDto reminderDto){
    return new Reminder(reminderDto.getReminderTitle(),
        reminderDto.getReminderDate(),
        reminderDto.getReminderDetails(),
        reminderDto.getCategory());
  }
}
