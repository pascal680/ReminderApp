package com.hampolo.reminderapp.controller;

import com.hampolo.reminderapp.dto.ReminderAddDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DataNotFoundException;
import com.hampolo.reminderapp.model.Reminder;
import com.hampolo.reminderapp.repository.ReminderRepository;
import com.hampolo.reminderapp.service.ReminderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
public class ReminderController {

  private final ReminderService reminderService;

  public ReminderController(ReminderService reminderService) {
    this.reminderService = reminderService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Reminder>> getAllReminders(){
    return new ResponseEntity<>(reminderService.getAllReminders(), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Reminder>> getAllUserReminders(@PathVariable String userId)
      throws AccountNotFoundException {
    return new ResponseEntity<>(reminderService.getAllUserReminder(userId), HttpStatus.OK);
  }

  @GetMapping("/{reminderId}")
  public ResponseEntity<Reminder> getReminder(@PathVariable String reminderId)
      throws DataNotFoundException {
    return new ResponseEntity<>(reminderService.getReminder(reminderId), HttpStatus.OK);
  }

  @PostMapping("/save")
  public ResponseEntity<Reminder> saveReminder(@RequestBody ReminderAddDto reminderAddDto)
      throws AccountNotFoundException {
    return new ResponseEntity<>(reminderService.saveReminder(reminderAddDto), HttpStatus.OK);
  }
}
