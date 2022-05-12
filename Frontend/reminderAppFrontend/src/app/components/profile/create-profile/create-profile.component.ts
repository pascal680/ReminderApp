import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validator, Validators} from "@angular/forms";
import {UserService} from "../../../services/user.service";
import {BehaviorSubject, map, Observable, of, shareReplay, switchMap, tap} from "rxjs";
import {SwalService} from "../../../services/swal.service";

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

  private passwordConfirmedSubject = new BehaviorSubject<boolean>(true);
  passwordConfirmed$ = this.passwordConfirmedSubject.asObservable();

  userProfileForm: FormGroup;



  constructor(private userService: UserService, private swalService: SwalService) { }

  ngOnInit(): void {
    this.initUserProfileForm();
    this.passwordConfirmed();
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

  passwordConfirmed(): void{
    this.userProfileForm.get("passwordConfirmation").valueChanges.pipe(tap(value => {
      console.log("sees changes")
      this.passwordConfirmedSubject.next(value === this.userProfileForm.get("password").dirty);
        })).subscribe();
  }

  public onSubmitForm() {

      console.log("submit form");
      if(this.userProfileForm.valid) {
        console.log("valid");
        this.passwordConfirmed$.pipe(tap((confirmed)=> console.log("entering", confirmed)),switchMap(confirmed => {
          if (confirmed) {
            console.log("confirmed");
            let userProfile = this.userProfileForm.value;
            userProfile = {...userProfile, ...this.getRemindByOptions()};
            delete userProfile.passwordConfirmation;
            delete userProfile.remindBy;
            return this.userService.registerUser(userProfile);
          }
          return of(null);
        }))
          .subscribe((data) =>{ if(data) {
            this.swalService.success("1", "2", "success", 1000).then(() => console.log("success"))
          }
        });
      }else{
        console.log("notvalid");
      }
      // this.userService.registerUser(this.userProfileForm.value)
  }
}
