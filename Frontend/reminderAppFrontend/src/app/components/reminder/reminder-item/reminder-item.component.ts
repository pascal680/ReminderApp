import {Component, Input, OnInit} from '@angular/core';
import {Reminder} from "../../../models/entities.model";
import {Router} from "@angular/router";
import Swal from "sweetalert2";
import {ReminderStore} from "../../../services/reminder.store";

@Component({
  selector: 'app-reminder-item',
  templateUrl: './reminder-item.component.html',
  styleUrls: ['./reminder-item.component.css']
})
export class ReminderItemComponent implements OnInit {

  @Input() reminder: Reminder;
  constructor(private router: Router, private reminderStore: ReminderStore) {

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
      .slice(0,2).join(':') + timeStamp

  }

  get isAllDay(): boolean{
    return this.reminder.isAllDay
  }

  public navigateToReminder(): void {
    this.router.navigate(['/reminder'],
      {
        queryParams: {id: this.reminder.id}
      });
  }

  public deleteReminder(): void {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.value) {
        this.reminderStore.deleteReminder(this.reminder.id).subscribe(
          (result) => {
            if(result){
              Swal.fire(
                'Deleted!',
                'Your file has been deleted.',
                'success'
              )
              return;
            }
            Swal.fire(
              'Not Deleted!',
              'We were unable to delete this reminder.',
              'error'
            )
          }
        )
      }
    })
  }



}
