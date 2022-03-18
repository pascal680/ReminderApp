import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Category, User} from "../models/entities.model";
import {BaseService} from "./base.service";
import {BehaviorSubject, map, Observable, shareReplay, tap} from "rxjs";
import {LoginRequest} from "../models/request/login-request";
import {UserRegistrationFormRequest} from "../models/request/user-registration-form-request";
import {AddCategoryRequest} from "../models/request/add-category-request";

const AUTH_DATA = "user_data"

@Injectable({
  providedIn: "root"
})
export class UserService extends BaseService {

  // @ts-ignore
  public userSubject = new BehaviorSubject<User>(null);

  user$: Observable<User> = this.userSubject.asObservable();

  isLoggedIn$: Observable<boolean>;
  isLoggedOut$: Observable<boolean>;


  constructor(private http: HttpClient) {
    super();
    this.isLoggedIn$ = this.user$.pipe(map(user => !!user), shareReplay());
    this.isLoggedOut$ = this.isLoggedIn$.pipe(map(loggedIn => !loggedIn), shareReplay());

    const user = localStorage.getItem(AUTH_DATA);

    if (user) {
      this.userSubject.next(JSON.parse(user));
    }
  }

  private get serviceApiGatewayUrl(): string {
    return this.apiGatewayUrl + "/account";
  }

  public getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.serviceApiGatewayUrl}/users/all`);
  }

  public login(loginRequest: LoginRequest): Observable<User> {
    return this.http.post(`${this.serviceApiGatewayUrl}/login`, loginRequest).pipe(
      tap(user => {
        this.userSubject.next(user);
        localStorage.setItem(AUTH_DATA, JSON.stringify(user));
      }),
      shareReplay()
    );
  }

  public logout() {
    // @ts-ignore
    this.userSubject.next(null);
    localStorage.removeItem(AUTH_DATA);
  }

  public registerUser(userRegistrationFormRequest: UserRegistrationFormRequest): Observable<User> {
    return this.http.post(`${this.serviceApiGatewayUrl}/user/register`, userRegistrationFormRequest)
  }

  public addUserCategory(addCategoryRequest: AddCategoryRequest): Observable<Category> {
    return this.http.post(`${this.serviceApiGatewayUrl}/user/category/save`, addCategoryRequest)
  }

  public getUserCategories(userId: string): Observable<Category[]> {
    return this.http.get<User[]>(`${this.serviceApiGatewayUrl}/user/${userId}/categories`);
  }
}
