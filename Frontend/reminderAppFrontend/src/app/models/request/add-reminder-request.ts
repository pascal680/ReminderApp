import {Category, ReminderDetails} from "../entities.model";

export interface AddReminderRequest{

  userId: string;
  reminderTitle: string;
  reminderDate: number;
  reminderDetails?: ReminderDetails;
  category?: Category;
}
