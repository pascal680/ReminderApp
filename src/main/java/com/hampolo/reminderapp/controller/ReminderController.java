package com.hampolo.reminderapp.controller;

import com.hampolo.reminderapp.model.Reminder;
import com.hampolo.reminderapp.repository.ReminderRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
public class ReminderController {

  private ReminderRepository reminderRepository;

  public ReminderController(ReminderRepository reminderRepository) {
    this.reminderRepository = reminderRepository;
  }

  @GetMapping("/all")
  public List<Reminder> getAllReminders(){
    return reminderRepository.findAll();
  }
}
