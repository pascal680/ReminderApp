package com.hampolo.reminderapp.controllerTests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hampolo.reminderapp.controller.AccountController;
import com.hampolo.reminderapp.controller.ReminderController;
import com.hampolo.reminderapp.dto.ReminderAccesDto;
import com.hampolo.reminderapp.dto.ReminderAddDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DataNotFoundException;
import com.hampolo.reminderapp.mapping.ReminderMapper;
import com.hampolo.reminderapp.model.Reminder;
import com.hampolo.reminderapp.service.ReminderService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(
    classes = ReminderController.class,
    initializers = ConfigFileApplicationContextInitializer.class)
@WebMvcTest(ReminderController.class)
public class ReminderControllerTest {

  private final ObjectMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReminderService reminderService;

  public ReminderControllerTest() {
    this.mapper = new ObjectMapper().findAndRegisterModules();
    this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void testGetAllReminders() throws Exception {
    List<ReminderAccesDto> expected = getReminders().stream().map(ReminderMapper::toVueAccess).collect(
        Collectors.toList());
    when(reminderService.getAllReminders()).thenReturn(expected);

    MvcResult result = mockMvc.perform(get("/reminder/all")
        .contentType("application/json"))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testGetAllUserReminders() throws Exception {
    List<ReminderAccesDto> expected = getReminders().stream().map(ReminderMapper::toVueAccess).collect(
        Collectors.toList());
    when(reminderService.getAllUserReminders(any(String.class))).thenReturn(expected);

    MvcResult result = mockMvc.perform(get("/reminder/user/test")
        .contentType("application/json"))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testGetAllUserRemindersNoUser() throws Exception {
    when(reminderService.getAllUserReminders(any(String.class))).thenThrow(AccountNotFoundException.class);
    MvcResult result = mockMvc.perform(get("/reminder/user/test")
        .contentType("application/json"))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void testGetReminder() throws Exception {
    ReminderAccesDto expected = ReminderMapper.toVueAccess(getReminders().get(0));
    when(reminderService.getReminder(any(String.class))).thenReturn(expected);

    MvcResult result = mockMvc.perform(get("/reminder/1")
        .contentType("application/json"))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testGetReminderNotFound() throws Exception {
    when(reminderService.getReminder(any(String.class))).thenThrow(DataNotFoundException.class);
    MvcResult result = mockMvc.perform(get("/reminder/1")
        .contentType("application/json"))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }


  @Test
  public void testSaveReminder() throws Exception {
    ReminderAccesDto expected = ReminderMapper.toVueAccess(getReminders().get(0));
    when(reminderService.saveReminder(any(ReminderAddDto.class))).thenReturn(expected);

    MvcResult result = mockMvc.perform(get("/reminder/save")
        .contentType("application/json"))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testSaveReminderNoUser() throws Exception {
    ReminderAddDto reminderAddDto = new ReminderAddDto();
    when(reminderService.saveReminder(reminderAddDto)).thenThrow(AccountNotFoundException.class);
    MvcResult result = mockMvc.perform(post("/reminder/save")
        .contentType("application/json")
        .content(mapper.writeValueAsString(reminderAddDto)))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }



  private List<Reminder> getReminders() {
    return List.of(
        new Reminder("Test Reminder", LocalDateTime.now(), true),
        new Reminder("Test Reminder2", LocalDateTime.now(), true)
    );
  }
}
