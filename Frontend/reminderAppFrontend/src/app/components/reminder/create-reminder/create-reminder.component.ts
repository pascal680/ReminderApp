import { Component, OnInit } from '@angular/core';
import {catchError, combineLatest, map, Observable, shareReplay, switchMap, tap} from "rxjs";
import {Category, Reminder} from "../../../models/entities.model";
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ReminderService} from "../../../services/reminder-service";
import {UserService} from "../../../services/user-service";
import {AddReminderRequest} from "../../../models/request/add-reminder-request";


@Component({
  selector: 'app-create-reminder',
  templateUrl: './create-reminder.component.html',
  styleUrls: ['./create-reminder.component.css']
})
export class CreateReminderComponent implements OnInit {

  categories$: Observable<Category[]>

  reminderForm: FormGroup;

  constructor(private reminderService: ReminderService,
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
      reminderCategory: new FormControl(),
      reminderDescription: new FormControl()
    });
  }

  public onSubmitForm(): void{
    this.userService.user$.pipe(
      map(user => { const addReminderRequest: AddReminderRequest ={
        userId: user.id,
        reminderTitle: this.reminderForm.get('reminderTitle').value,
        reminderDate: new Date(this.reminderForm.get('reminderDate').value).getTime(),
        reminderDetails:{
          description: this.reminderForm.get('reminderDescription').value
        },
        category:{
          title: this.reminderForm.get('reminderCategory').value
        }
      }
      return addReminderRequest
      }),
      switchMap(addReminderRequest => this.reminderService.addReminder(addReminderRequest)),
      tap(returnedReminder => console.log(returnedReminder, "returned!!!")),
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





}
