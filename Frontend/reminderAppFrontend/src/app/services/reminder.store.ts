import {Injectable} from "@angular/core";
import {ReminderService} from "./reminder.service";
import {BehaviorSubject, map, Observable, tap} from "rxjs";
import {Reminder} from "../models/entities.model";
import {AddReminderRequest} from "../models/request/add-reminder-request";

@Injectable({
  providedIn: 'root'
})
export class ReminderStore {

  private reminderSubject = new BehaviorSubject<Reminder[]>([])

  reminders$: Observable<Reminder[]> = this.reminderSubject.asObservable()

  constructor(private reminderService: ReminderService) {
    this.loadAllReminders()
  }

  private loadAllReminders() {
    this.reminderService.getAllReminders().pipe(
      tap(reminders => this.reminderSubject.next(reminders))
    ).subscribe();
  }

  public addCourse(addReminderRequest: AddReminderRequest) {
    this.reminderService.addReminder(addReminderRequest).subscribe(
      () => this.loadAllReminders()
    )
  }

  public deleteReminder(reminderId: string) {
    this.reminderService.deleteReminder(reminderId).subscribe((result) => {
        if (result) {
          const reminders = this.reminderSubject.getValue();
          const index = reminders.findIndex(reminder => reminder.id === reminderId);
          reminders.splice(index);
          this.reminderSubject.next(reminders);
        }
      }
    )
  }

  public filterByCategory(category: string): Observable<Reminder[]> {
    return this.reminders$.pipe(map(reminders => reminders.filter(reminder => reminder.reminderCategory === category)))
  }

  public refreshReminders() {
    this.loadAllReminders()
  }
}
