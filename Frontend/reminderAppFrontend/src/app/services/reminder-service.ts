import {Injectable} from "@angular/core";
import {BaseService} from "./base.service";
import {Observable} from "rxjs";
import {Reminder} from "../models/entities.model";
import {HttpClient} from "@angular/common/http";
import * as http from "http";
import {AddReminderRequest} from "../models/request/add-reminder-request";

@Injectable({
  providedIn:"root"
})
export class ReminderService extends BaseService{

  constructor(private http: HttpClient) {
    super();
  }

  private get serviceApiGatewayUrl(): string {
    return this.apiGatewayUrl + "/reminder";
  }

  public getAllReminders(): Observable<Reminder[]>{
    return this.http.get<Reminder[]>(`${this.serviceApiGatewayUrl}/all`)
  }

  public getAllUserReminders(userId: string): Observable<Reminder[]>{
    return this.http.get<Reminder[]>(`${this.serviceApiGatewayUrl}/user/${userId}`)
  }

  public getReminder(reminderId: String): Observable<Reminder>{
    return this.http.get<Reminder>(`${this.serviceApiGatewayUrl}/${reminderId}`)
  }

  public addReminder(addReminderRequest: AddReminderRequest): Observable<Reminder>{
    return this.http.post(`${this.serviceApiGatewayUrl}/save`, addReminderRequest)
  }
}
