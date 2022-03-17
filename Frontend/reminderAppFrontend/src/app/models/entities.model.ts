import {Priority, Role} from "./enums.model";

export interface User{
  id?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  // password?: string;
  phoneNumber?: string;
  role?: Role;
  reminders?: Reminder[];
  categories?: Category[];
}

export interface Admin{
  id?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  // password?: string;
  phoneNumber?: string;
  role?: Role;
}

export interface Reminder{
  id?: string;
  reminderTitle?: string;
  reminderDate?: Date;
  reminderCreationDate?: Date;
  reminderDetails?: ReminderDetails;
  reminderCategory?: Category;
}

export interface ReminderDetails{
  description?: string;
  priorityLevel?: Priority;
}

export interface Category{
  id?: string;
  title?: string;
  description?: string
}



