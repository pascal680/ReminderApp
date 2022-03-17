package com.hampolo.reminderapp.service;

import com.hampolo.reminderapp.dto.ReminderAddDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DataNotFoundException;
import com.hampolo.reminderapp.mapping.ReminderMapper;
import com.hampolo.reminderapp.model.Reminder;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.ReminderRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {

  private final ReminderRepository reminderRepository;
  private final UserRepository userRepository;
  private final AccountService accountService;

  public ReminderService(ReminderRepository reminderRepository,
      UserRepository userRepository, AccountService accountService) {
    this.reminderRepository = reminderRepository;
    this.userRepository = userRepository;
    this.accountService = accountService;
  }

  public Reminder saveReminder(ReminderAddDto reminderAddDto) throws AccountNotFoundException {
    Optional<User> user = userRepository.findById(reminderAddDto.getUserId());

    if(user.isEmpty()){
      throw new AccountNotFoundException("Reminder user could not be found. Reminder: "+ reminderAddDto);
    }

    List<Reminder> userReminders = user.map(User::getReminders).get();
    Reminder reminderEntity = ReminderMapper.toEntity(reminderAddDto);
    Reminder returnedreminder = reminderRepository.save(reminderEntity);

    userReminders.add(returnedreminder);

    user.get().setReminders(userReminders);

    accountService.saveUser(user.get());

    return returnedreminder;

//    return user.map(u -> u.setReminders(()->u.getReminders().add(ReminderMapper.toEntity(reminderAddDto));
  }

  public List<Reminder> getAllReminders(){
    return reminderRepository.findAll();
  }

  public List<Reminder> getAllUserReminder(String userId) throws AccountNotFoundException {
    return userRepository.findById(userId)
        .map(User::getReminders)
        .orElseThrow(()->new AccountNotFoundException("Account was not found")).stream().sorted(Comparator.comparing(Reminder::getReminderDate)).collect(Collectors.toList());
  }

  public Reminder getReminder(String reminderId) throws DataNotFoundException {
    return reminderRepository.findById(reminderId).orElseThrow(()->new DataNotFoundException("The data could not be found"));
  }


}
