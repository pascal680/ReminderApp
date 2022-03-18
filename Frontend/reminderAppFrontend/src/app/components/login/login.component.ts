import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user-service";
import {catchError, tap} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  errorMessage: string = undefined;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.initLoginForm();
  }

  private initLoginForm(): void {
    this.loginForm = new FormGroup({
      email: new FormControl(),
      password: new FormControl()
    })
  }

  public onSubmitForm(): void {
    this.userService.login({
      email: this.loginForm.get('email').value,
      password: this.loginForm.get('password').value
    }).pipe(
      tap(user => console.log("logged in user", user)),
      //@ts-ignore
      catchError(error => {
        if (error.status == 401) {
          this.errorMessage = "Incorrect password";
        }
        if (error.status == 404) {
          this.errorMessage = "Wrong Credentials!";
        }
      }),
    ).subscribe(()=> this.router.navigateByUrl("/home"))


  }


}
