import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {catchError, map, switchMap, tap} from "rxjs";
import {ReminderService} from "../../../services/reminder-service";

@Component({
  selector: 'app-reminder-details',
  templateUrl: './reminder-details.component.html',
  styleUrls: ['./reminder-details.component.css']
})
export class ReminderDetailsComponent implements OnInit {

  constructor(private route: ActivatedRoute, private reminderService: ReminderService) { }

  ngOnInit(): void {
    this.route.queryParams.pipe(
      tap(params => console.log(params, " params received")),
      switchMap(params => this.reminderService.getReminder(params['reminderId'])),
      tap(reminder => console.log(reminder, "reminder received")),
      //@ts-ignore
      catchError(error => {
        if(error.status == 404){
          alert("Could not fetch the reminder");
        }
      })
    ).subscribe()
  }



}
