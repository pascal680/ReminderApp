import {Component, Input, OnInit} from '@angular/core';
import {Reminder} from "../../../models/entities.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-reminder-item',
  templateUrl: './reminder-item.component.html',
  styleUrls: ['./reminder-item.component.css']
})
export class ReminderItemComponent implements OnInit {

  @Input() reminder: Reminder;
  constructor(private router: Router) {

  }

  ngOnInit(): void {
    console.log(this.reminder, "current reminder")
  }

  get reminderDate(): string{
    return this.reminder.reminderDate.getFullYear().toString()
      + "-"
      + (this.reminder.reminderDate.getUTCMonth() +1).toString()
      + "-"
      + this.reminder.reminderDate.getUTCDate().toString();
  }

  get time(): string{
    const timeStamp = this.reminder.reminderDate.toLocaleTimeString().split(" ")[1]
    return this.reminder.reminderDate.toLocaleTimeString()
      .split(":")
      .slice(0,2).join(':') + timeStamp;
  }

  public navigateToReminder(): void {
    this.router.navigate(['/reminder'],
      {
        queryParams: {id: this.reminder.id}
      })
  }



}
