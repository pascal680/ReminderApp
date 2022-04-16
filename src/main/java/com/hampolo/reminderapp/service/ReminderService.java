package com.hampolo.reminderapp.service;

import com.hampolo.reminderapp.dto.ReminderAccesDto;
import com.hampolo.reminderapp.dto.ReminderAddDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DataNotFoundException;
import com.hampolo.reminderapp.mapping.ReminderMapper;
import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.Reminder;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.CategoryRepository;
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
  private final CategoryRepository categoryRepository;
  private final AccountService accountService;

  public ReminderService(ReminderRepository reminderRepository,
      UserRepository userRepository,
      CategoryRepository categoryRepository,
      AccountService accountService) {
    this.reminderRepository = reminderRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
    this.accountService = accountService;
  }

  public ReminderAccesDto saveReminder(ReminderAddDto reminderAddDto) throws AccountNotFoundException {
    Optional<User> user = userRepository.findById(reminderAddDto.getUserId());

    if(user.isEmpty()){
      throw new AccountNotFoundException("Reminder user could not be found. Reminder: "+ reminderAddDto);
    }

    List<Reminder> userReminders = user.map(User::getReminders).get();
    Reminder reminderEntity = ReminderMapper.toEntity(reminderAddDto);
    if(categoryRepository.findById(reminderEntity.getReminderCategory().getId()).equals(Optional.empty())){
      Category savedCategory = categoryRepository.save(reminderAddDto.getReminderCategory());
      reminderEntity.setReminderCategory(savedCategory);
    }
    Reminder returnedreminder = reminderRepository.save(reminderEntity);

    userReminders.add(returnedreminder);

    user.get().setReminders(userReminders);

    accountService.saveUser(user.get());

    return ReminderMapper.toVueAccess(returnedreminder);

//    return user.map(u -> u.setReminders(()->u.getReminders().add(ReminderMapper.toEntity(reminderAddDto));
  }

  public List<ReminderAccesDto> getAllReminders(){
    return reminderRepository.findAll()
        .stream().map(ReminderMapper::toVueAccess)
        .collect(Collectors.toList());
  }

  public List<ReminderAccesDto> getAllUserReminder(String userId) throws AccountNotFoundException {
    return userRepository.findById(userId)
        .map(User::getReminders)
        .orElseThrow(()->new AccountNotFoundException("Account was not found"))
        .stream().sorted(Comparator.comparing(Reminder::getReminderDate))
        .map(ReminderMapper::toVueAccess)
        .collect(Collectors.toList());
  }

  public ReminderAccesDto getReminder(String reminderId) throws DataNotFoundException {
    return reminderRepository.findById(reminderId)
        .map(ReminderMapper::toVueAccess)
        .orElseThrow(()->new DataNotFoundException("The data could not be found"));
  }

  public boolean deleteReminder(String reminderId) throws DataNotFoundException {
    if(reminderRepository.findById(reminderId).isPresent()){
      reminderRepository.deleteById(reminderId);
      return true;
    }
    throw new DataNotFoundException("The data could not be found");
  }

  public ReminderAccesDto updateReminder(ReminderAccesDto reminderAccesDto) throws DataNotFoundException {
    Optional<Reminder> reminder = reminderRepository.findById(reminderAccesDto.getId());

    if(reminder.isEmpty()){
      throw new DataNotFoundException("The data could not be found");
    }

    Reminder reminderEntity = ReminderMapper.toEntity(reminderAccesDto);
    if(categoryRepository.findById(reminderEntity.getReminderCategory().getId()).equals(Optional.empty())){
      Category savedCategory = categoryRepository.save(reminderAccesDto.getReminderCategory());
      reminderEntity.setReminderCategory(savedCategory);
    }
    Reminder returnedreminder = reminderRepository.save(reminderEntity);

    return ReminderMapper.toVueAccess(returnedreminder);
  }


}
