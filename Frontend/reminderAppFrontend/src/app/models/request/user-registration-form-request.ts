import {Time} from "@angular/common";

export interface UserRegistrationFormRequest{
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: string
  remindByEmail: boolean;
  remindByPhone: boolean;
  remindedAtTime: Time;
}
