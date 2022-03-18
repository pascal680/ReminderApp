package com.hampolo.reminderapp.mapping;

import com.hampolo.reminderapp.dto.ReminderAccesDto;
import com.hampolo.reminderapp.dto.ReminderAddDto;
import com.hampolo.reminderapp.model.Reminder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class ReminderMapper {

  public static Reminder toEntity(ReminderAddDto reminderDto){
    return new Reminder(reminderDto.getReminderTitle(),
        Instant.ofEpochSecond(reminderDto.getReminderDate(),0).atOffset(ZoneOffset.UTC).toLocalDateTime(),
        reminderDto.getReminderDetails(),
        reminderDto.getReminderCategory());
  }

  public static ReminderAccesDto toVueAccess(Reminder reminder){
    return new ReminderAccesDto(reminder.getId(),
        reminder.getReminderTitle(),
        reminder.getReminderDate().toEpochSecond(ZoneOffset.UTC),
        reminder.getReminderCreationDate().toEpochSecond(ZoneOffset.UTC),
        reminder.getReminderDetails(),
        reminder.getReminderCategory());
  }
}
