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

  reminders$ : Observable<Reminder[]>;
  constructor(private reminderService: ReminderService, private userService: UserService) { }

  ngOnInit(): void {
    this.initReminders();
  }

  private initReminders(): void{
    this.reminders$ = this.userService.user$.pipe(
      switchMap(user=>this.reminderService.getAllUserReminders(user.id))
  );
}

}
