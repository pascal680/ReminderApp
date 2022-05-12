import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { ReminderListComponent } from './components/reminder/reminder-list/reminder-list.component';
import { ReminderItemComponent } from './components/reminder/reminder-item/reminder-item.component';
import { CreateReminderComponent } from './components/reminder/create-reminder/create-reminder.component';
import { ReminderDetailsComponent } from './components/reminder/reminder-details/reminder-details.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { CreateProfileComponent } from './components/profile/create-profile/create-profile.component';
import Swal from "sweetalert2";
import {SwalService} from "./services/swal.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    ReminderListComponent,
    ReminderItemComponent,
    CreateReminderComponent,
    ReminderDetailsComponent,
    CreateProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [SwalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
