import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/entities.model";
import {BaseService} from "./base.service";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn:"root"
})
export class UserService extends BaseService{

  constructor(private http: HttpClient) {
    super();
  }

  private get serviceApiGatewayUrl(): string{
    return this.apiGatewayUrl+"/account";
  }

  public getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.serviceApiGatewayUrl}/users/all`).pipe(
      tap((users)=>console.log(users))
    )
  }
}
