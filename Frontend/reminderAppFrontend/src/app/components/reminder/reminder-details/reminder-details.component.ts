import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {catchError, combineLatest, map, Observable, shareReplay, switchMap, tap} from "rxjs";
import {ReminderService} from "../../../services/reminder-service";
import {Category, Reminder} from "../../../models/entities.model";
import {FormControl, FormGroup} from "@angular/forms";
import {UserService} from "../../../services/user-service";

interface ReminderFormData{
  reminder: Reminder;
  categories: Category[];
}
@Component({
  selector: 'app-reminder-details',
  templateUrl: './reminder-details.component.html',
  styleUrls: ['./reminder-details.component.css']
})
export class ReminderDetailsComponent implements OnInit{

  reminder$ : Observable<Reminder>;
  categories$: Observable<Category[]>
  reminderFormData$: Observable<ReminderFormData>

  reminderForm: FormGroup;
  constructor(private route: ActivatedRoute,
              private reminderService: ReminderService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.initReminderFormData();
    this.initReminderForm();
  }


  private initReminder(): void{
    this.reminder$ = this.route.queryParams.pipe(
      switchMap(params => this.reminderService.getReminder(params['id'])),
      //@ts-ignore
      catchError(error => {
        if(error.status == 404){
          alert("Could not fetch the reminder");
        }
      }),
      shareReplay()
    );
  }

  private initCategories(): void{
    this.categories$ = this.userService.user$.pipe(
      map(user => user.categories)
    );
  }

  private initReminderFormData(): void{
    this.initReminder();
    this.initCategories();
    this.reminderFormData$ = combineLatest([this.reminder$, this.categories$]).pipe(
      map(([reminder, categories]) => {
        return {
          reminder: reminder,
          categories: categories
        }
      }),
      tap(obj => console.log(obj, "data"))
    );
  }
  private initReminderForm(): void{
    this.reminder$.subscribe(reminder => {

    this.reminderForm = new FormGroup({
      reminderTitle: new FormControl(reminder?.reminderTitle),
      reminderDate: new FormControl(reminder?.reminderDate.toISOString().slice(0,10)),
      reminderCategory: new FormControl(reminder?.reminderCategory.title),
      reminderDescription: new FormControl(reminder.reminderDetails?.description)
    });
    })
  }





}
