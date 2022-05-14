import {Injectable} from "@angular/core";
import {ReminderService} from "./reminder.service";
import {BehaviorSubject, map, Observable, of, switchMap, tap} from "rxjs";
import {Reminder} from "../models/entities.model";
import {AddReminderRequest} from "../models/request/add-reminder-request";
import {UserService} from "./user.service";
import {Role} from "../models/enums.model";
import {ReminderResponse} from "../models/response/reminder-response";

@Injectable({
  providedIn: 'root'
})
export class ReminderStore {

  private reminderSubject = new BehaviorSubject<Reminder[]>([])

  reminders$: Observable<Reminder[]> = this.reminderSubject.asObservable()

  constructor(private reminderService: ReminderService, private userService: UserService) {
    this.loadAllReminders()
  }

  private loadAllReminders() {
    this.userService.user$.pipe(switchMap(user => {
        if (user) {
          if (user.role === Role.Admin) {
            console.log('Admin', user)
            return this.reminderService.getAllReminders()
          }
          console.log('User', user)
          return this.reminderService.getAllUserReminders(user.id)
        }
        return of([])
      }),
      tap(console.log),
      tap(reminders => this.reminderSubject.next(reminders))).subscribe();
  }


  public addReminder(addReminderRequest: AddReminderRequest): Observable<Reminder> {
    return this.reminderService.addReminder(addReminderRequest).pipe(
      tap(
      () => this.loadAllReminders()
    )
    )
  }

  public deleteReminder(reminderId: string): Observable<boolean> {
    return this.reminderService.deleteReminder(reminderId).pipe(tap((result) => {
        if (result) {
          const reminders = this.reminderSubject.getValue();
          const index = reminders.findIndex(reminder => reminder.id === reminderId);
          reminders.splice(index);
          console.log("spliced reminders", reminders);
          this.reminderSubject.next(reminders);
        }
      }
    ))
  }

  public updateReminder(reminder: ReminderResponse): Observable<Reminder> {
    return this.reminderService.updateReminder(reminder).pipe(
      tap(() => this.loadAllReminders()));
  }


  public filterByCategory(category: string): Observable<Reminder[]> {
    return this.reminders$.pipe(map(reminders => reminders.filter(reminder => reminder.reminderCategory === category)))
  }

  public refreshReminders() {
    this.loadAllReminders()
  }
}
