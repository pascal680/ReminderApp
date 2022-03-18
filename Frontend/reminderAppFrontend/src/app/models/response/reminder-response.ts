import {Category, ReminderDetails} from "../entities.model";

export interface ReminderResponse {
  id?: string;
  reminderTitle?: string;
  reminderDate?: number;
  reminderCreationDate?: number;
  reminderDetails?: ReminderDetails;
  reminderCategory?: Category;
}
