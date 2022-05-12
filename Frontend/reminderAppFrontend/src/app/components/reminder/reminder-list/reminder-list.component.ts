import { Component, OnInit } from '@angular/core';
import {ReminderStore} from "../../../services/reminder.store";

@Component({
  selector: 'app-reminder-list',
  templateUrl: './reminder-list.component.html',
  styleUrls: ['./reminder-list.component.css']
})
export class ReminderListComponent implements OnInit {

  constructor(public reminderStore: ReminderStore) { }

  ngOnInit(): void {
  }



}
