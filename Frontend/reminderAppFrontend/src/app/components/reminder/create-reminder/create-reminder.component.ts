import { Component, OnInit } from '@angular/core';
import {catchError, combineLatest, map, Observable, shareReplay, switchMap, tap} from "rxjs";
import {Category, Reminder} from "../../../models/entities.model";
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {AddReminderRequest} from "../../../models/request/add-reminder-request";
import {ReminderStore} from "../../../services/reminder.store";

const MILLISECONDS_TO_SECONDS_CONVERSION = 1000;

@Component({
  selector: 'app-create-reminder',
  templateUrl: './create-reminder.component.html',
  styleUrls: ['./create-reminder.component.css']
})
export class CreateReminderComponent implements OnInit {

  categories$: Observable<Category[]>

  reminderForm: FormGroup;

  constructor(private reminderStore: ReminderStore,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.initCategories();
    this.initReminderForm();
  }

  private initCategories(): void{
    this.categories$ = this.userService.user$.pipe(
      map(user => user.categories),
      tap(categories => console.log("the user categories", categories))
    );
  }

  private initReminderForm(): void{
    this.reminderForm = new FormGroup({
      reminderTitle: new FormControl(),
      reminderDate: new FormControl(),
      reminderTime: new FormControl(),
      allDay: new FormControl(),
      reminderCategory: new FormControl(),
      reminderDescription: new FormControl()
    });
  }

  private get reminderDateCreate(): string {
    return this.reminderForm.get('allDay').value?
      this.reminderForm.get('reminderDate').value :
      this.reminderForm.get('reminderDate').value + " " + this.reminderForm.get('reminderTime').value;
  }



  public onSubmitForm(): void{
    console.log(this.reminderForm.value, 'form values')
    this.userService.user$.pipe(
      map(user => { const addReminderRequest: AddReminderRequest ={
        userId: user.id,
        reminderTitle: this.reminderForm.get('reminderTitle').value,
        reminderDate: new Date(this.reminderDateCreate).getTime()/ MILLISECONDS_TO_SECONDS_CONVERSION,
        allDay: this.reminderForm.get('allDay').value,
        reminderDetails:{
          description: this.reminderForm.get('reminderDescription').value
        },
        reminderCategory:{
          id: this.reminderForm.get('reminderCategory').value?.id,
          title: this.reminderForm.get('reminderCategory').value?.title
        }
      }
      return addReminderRequest
      }),
      switchMap(addReminderRequest => this.reminderStore.addReminder(addReminderRequest)),
      //@ts-ignore TODO fix this
      catchError(error => {
        if(error.status == 404){
          alert("Was not able to add reminder");
        }
      })
    ).subscribe(() => {
      this.router.navigateByUrl("/home");
    })
  }

  get isAllDay(): boolean {
    return this.reminderForm.get('allDay').value;
  }

}
