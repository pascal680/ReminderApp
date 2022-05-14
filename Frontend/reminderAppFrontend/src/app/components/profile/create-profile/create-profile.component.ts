import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validator, Validators} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {BehaviorSubject, map, Observable, of, shareReplay, switchMap, tap} from "rxjs";
import {SwalService} from "../../../services/swal.service";
import {Router} from "@angular/router";

interface remindByOptions {
  remindByPhone: boolean;
  remindByEmail: boolean;
}

@Component({
  selector: 'app-create-profile',
  templateUrl: './create-profile.component.html',
  styleUrls: ['./create-profile.component.css']
})
export class CreateProfileComponent implements OnInit {


  userProfileForm: FormGroup;



  constructor(private userService: UserService, private swalService: SwalService, private router: Router) { }

  ngOnInit(): void {
    this.initUserProfileForm();
  }

  private initUserProfileForm() {
    this.userProfileForm = new FormGroup({
      firstName: new FormControl('',Validators.required),
      lastName: new FormControl('',Validators.required),
      email: new FormControl('',Validators.required),
      phoneNumber: new FormControl('',Validators.required),
      password: new FormControl('',Validators.required),
      passwordConfirmation: new FormControl('',Validators.required),
      remindBy: new FormControl('',Validators.required),
      remindedAtTime: new FormControl('',Validators.required)});
  }

  private getRemindByOptions(): remindByOptions{
    switch(this.userProfileForm.get('remindBy').value) {
      case 'phone':
        return {remindByEmail: false, remindByPhone: true} as remindByOptions;
      case 'email':
        return {remindByEmail: true, remindByPhone: false} as remindByOptions;
      case 'both':
        return {remindByEmail: true, remindByPhone: true} as remindByOptions;
      default:
        return {remindByEmail: false, remindByPhone: true} as remindByOptions;
    }
  }

  passwordConfirmed(): Observable<boolean> {
    if(this.userProfileForm.get("passwordConfirmation").dirty){
      return of(this.userProfileForm.get('password').value === this.userProfileForm.get('passwordConfirmation').value)
    }
    return of(true);
  }

  public onSubmitForm() {
      if(this.userProfileForm.valid) {
        this.passwordConfirmed().pipe(switchMap(confirmed => {
          if (confirmed) {
            console.log("confirmed");
            let userProfile = this.userProfileForm.value;
            userProfile = {...userProfile, ...this.getRemindByOptions()};
            delete userProfile.passwordConfirmation;
            delete userProfile.remindBy;
            return this.userService.registerUser(userProfile);
          }
          this.swalService.timedAlertError("Error", "Password confirmation failed");
          return of(null);
        }))
          .subscribe((data) =>{ if(data) {
            this.swalService.timedAlertSuccess("Account created", "Successfully created the account")
              .then(()=>this.router.navigate(['/login']));
          }
        });
      }else {
        this.swalService.timedAlertError("Error", "Please fill in all the fields");
      }
      // this.userService.registerUser(this.userProfileForm.value)
  }
}
