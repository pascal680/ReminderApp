package com.hampolo.reminderapp.serviceTests;

import com.hampolo.reminderapp.repository.ReminderRepository;
import com.hampolo.reminderapp.service.ReminderService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest {

  @Mock
  private ReminderRepository reminderRepository;

  @InjectMocks
  private ReminderService reminderService;


}
