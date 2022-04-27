import { Component, OnInit } from '@angular/core';
import {Observable, switchMap, tap} from "rxjs";
import {Reminder} from "../../../models/entities.model";
import {ReminderService} from "../../../services/reminder-service";
import {UserService} from "../../../services/user-service";

@Component({
  selector: 'app-reminder-list',
  templateUrl: './reminder-list.component.html',
  styleUrls: ['./reminder-list.component.css']
})
export class ReminderListComponent implements OnInit {

  constructor(public reminderService: ReminderService) { }

  ngOnInit(): void {
    this.reminderService.refreshReminders();
  }



}
