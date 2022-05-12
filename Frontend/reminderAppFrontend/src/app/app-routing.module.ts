import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {ReminderListComponent} from "./components/reminder/reminder-list/reminder-list.component";
import {ReminderDetailsComponent} from "./components/reminder/reminder-details/reminder-details.component";
import {CreateReminderComponent} from "./components/reminder/create-reminder/create-reminder.component";
import {CreateProfileComponent} from "./components/profile/create-profile/create-profile.component";

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path:"home", component:ReminderListComponent},
  {path:"createProfile", component:CreateProfileComponent},
  {path:"reminder", component: ReminderDetailsComponent},
  {path:"createReminder", component: CreateReminderComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
