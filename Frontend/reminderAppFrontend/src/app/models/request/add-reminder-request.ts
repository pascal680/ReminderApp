import {Category, ReminderDetails} from "../entities.model";

export interface AddReminderRequest{

  userId: string;
  reminderTitle: string;
  reminderDate: Date;
  reminderDetails?: ReminderDetails[];
  category?: Category;
}
