import {ReminderResponse} from "../response/reminder-response";
import {Reminder} from "../entities.model";

const CONVERSION_MILLISECONDS_TO_SECONDS: number = 1000;

export class ReminderMapper {

  public static toEntity(reminderResponse: ReminderResponse): Reminder{
    return {
      id: reminderResponse.id,
      reminderTitle: reminderResponse.reminderTitle,
      reminderDate: new Date(reminderResponse.reminderDate * CONVERSION_MILLISECONDS_TO_SECONDS),
      reminderCreationDate: new Date(reminderResponse.reminderCreationDate * CONVERSION_MILLISECONDS_TO_SECONDS),
      reminderDetails: reminderResponse.reminderDetails,
      reminderCategory: reminderResponse.reminderCategory,
      isAllDay: reminderResponse.allDay,
      isCompleted: reminderResponse.completed,
    }
  }
}
