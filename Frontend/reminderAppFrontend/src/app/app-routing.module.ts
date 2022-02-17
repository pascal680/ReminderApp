import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {ReminderListComponent} from "./components/reminder/reminder-list/reminder-list.component";

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path:"home", component:ReminderListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
