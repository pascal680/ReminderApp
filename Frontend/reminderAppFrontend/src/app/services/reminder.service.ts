import {Injectable} from "@angular/core";
import {BaseService} from "./base.service";
import {map, Observable, switchMap, tap} from "rxjs";
import {Reminder} from "../models/entities.model";
import {HttpClient} from "@angular/common/http";
import {AddReminderRequest} from "../models/request/add-reminder-request";
import {ReminderMapper} from "../models/mapper/reminder.mapper";
import {ReminderResponse} from "../models/response/reminder-response";
import {UserService} from "./user.service";

@Injectable({
  providedIn:"root"
})
export class ReminderService extends BaseService{

  constructor(private http: HttpClient, private userService: UserService) {
    super();
  }

  private get serviceApiGatewayUrl(): string {
    return this.apiGatewayUrl + "/reminder";
  }

  public getAllReminders(): Observable<Reminder[]>{
    return this.http.get<ReminderResponse[]>(`${this.serviceApiGatewayUrl}/all`).pipe(
      map(reminderResponses => reminderResponses.map(reminder => ReminderMapper.toEntity(reminder))
      ));
  }

  public getAllUserReminders(userId: string): Observable<Reminder[]>{
    return this.http.get<ReminderResponse[]>(`${this.serviceApiGatewayUrl}/user/${userId}`).pipe(
      map(reminderResponses => reminderResponses.map(reminder => ReminderMapper.toEntity(reminder))
      ));
  }

  public getReminder(reminderId: String): Observable<Reminder>{
    return this.http.get<ReminderResponse>(`${this.serviceApiGatewayUrl}/${reminderId}`).pipe(
      map(reminderResponse => ReminderMapper.toEntity(reminderResponse)
      ));
  }

  public addReminder(addReminderRequest: AddReminderRequest): Observable<Reminder>{
    return this.http.post(`${this.serviceApiGatewayUrl}/save`, addReminderRequest).pipe(
      map(reminderResponse => ReminderMapper.toEntity(reminderResponse))
    );
  }

  public updateReminder(reminder: ReminderResponse): Observable<Reminder>{
    return this.http.put(`${this.serviceApiGatewayUrl}/update`, reminder).pipe(
      map(reminderResponse => ReminderMapper.toEntity(reminderResponse)));
  }

  public deleteReminder(reminderId: String): Observable<boolean>{
    return this.http.delete<boolean>(`${this.serviceApiGatewayUrl}/delete/${reminderId}`);
  }
}
