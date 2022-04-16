package com.hampolo.reminderapp.controllerTests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hampolo.reminderapp.controller.AccountController;
import com.hampolo.reminderapp.controller.ReminderController;
import com.hampolo.reminderapp.dto.ReminderAccesDto;
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
    List<ReminderAccesDto> expected = getReminders().stream().map(reminder -> ReminderMapper.toVueAccess(reminder)).collect(
        Collectors.toList());
    when(reminderService.getAllReminders()).thenReturn(expected);

    MvcResult result = mockMvc.perform(get("/reminder/all")
        .contentType("application/json"))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }



  private List<Reminder> getReminders() {
    return List.of(
        new Reminder("Test Reminder", LocalDateTime.now(), true),
        new Reminder("Test Reminder2", LocalDateTime.now(), true)
    );
  }
}
