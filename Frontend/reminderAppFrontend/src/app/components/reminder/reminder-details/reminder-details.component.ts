import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {catchError, combineLatest, map, Observable, of, shareReplay, switchMap, tap} from "rxjs";
import {ReminderService} from "../../../services/reminder.service";
import {Category, Reminder} from "../../../models/entities.model";
import {FormControl, FormGroup, Validator, Validators} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {ReminderStore} from "../../../services/reminder.store";
import {AddReminderRequest} from "../../../models/request/add-reminder-request";
import {SwalService} from "../../../services/swal.service";
import {ReminderResponse} from "../../../models/response/reminder-response";

const MILLISECONDS_TO_SECONDS_CONVERSION = 1000;

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

  reminder$ : Observable<Reminder> = of(null);
  categories$: Observable<Category[]> = of(null);
  reminderFormData$: Observable<ReminderFormData>

  reminderForm: FormGroup;
  constructor(private route: ActivatedRoute,
              private reminderService: ReminderService,
              private reminderStore: ReminderStore,
              private router: Router,
              private swalService: SwalService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.initReminderForm();
    this.initReminderFormData();


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
      tap(reminder => console.log(reminder)),
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
    this.reminder$.subscribe(() => {

    this.reminderForm = new FormGroup({
      reminderTitle: new FormControl('',Validators.required),
      reminderDate: new FormControl('',Validators.required),
      reminderTime: new FormControl('',Validators.required),
      allDay: new FormControl(),
      reminderCategory: new FormControl('',Validators.required),
      reminderDescription: new FormControl('',Validators.required)
    });
    })
  }

  public updateReminder(): void {
    console.log(this.reminderForm.value);
    if(this.reminderForm.valid) {
      // @ts-ignore
      // @ts-ignore
      this.reminder$.pipe(map(reminder => {
          const updateReminderRequest: ReminderResponse = {
            id: reminder.id,
            reminderTitle: this.reminderForm.get('reminderTitle').value,
            reminderDate: new Date(this.reminderDateCreate).getTime() / MILLISECONDS_TO_SECONDS_CONVERSION,
            reminderCreationDate: reminder.reminderCreationDate.getTime() / MILLISECONDS_TO_SECONDS_CONVERSION,
            allDay: this.reminderForm.get('allDay').value,
            reminderDetails: {
              description: this.reminderForm.get('reminderDescription').value
            },
            reminderCategory: {
              id: this.reminderForm.get('reminderCategory').value?.id,
              title: this.reminderForm.get('reminderCategory').value?.title
            }
          }
          return updateReminderRequest;
        }),
        switchMap(reminder => this.reminderService.updateReminder(reminder)),
        //@ts-ignore TODO: fix this
        catchError(error => {
          if (error.status == 404) {
            this.swalService.timedAlertError("Error", "Was not able to update reminder");
          }
        })
      ).subscribe(() => {
        this.swalService.timedAlertSuccess("Success", "Reminder updated successfully");

        this.router.navigate(['/reminders']);
      });
    }else {
      this.swalService.timedAlertError("Error", "Please fill all the fields");
    }

  }

  private get reminderDateCreate(): string {
    return this.reminderForm.get('allDay').value?
      this.reminderForm.get('reminderDate').value :
      this.reminderForm.get('reminderDate').value + " " + this.reminderForm.get('reminderTime').value;
  }

  get isAllDay(): boolean {
    return this.reminderForm.get('allDay').value;
  }


}
