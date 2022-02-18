import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {ReminderListComponent} from "./components/reminder/reminder-list/reminder-list.component";
import {ReminderDetailsComponent} from "./components/reminder/reminder-details/reminder-details.component";

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path:"home", component:ReminderListComponent},
  {path:"reminder", component: ReminderDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
