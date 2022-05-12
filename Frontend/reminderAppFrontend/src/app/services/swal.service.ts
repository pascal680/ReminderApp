import {Injectable} from "@angular/core";
import Swal, {SweetAlertResult} from "sweetalert2";

Injectable({
  providedIn: 'root',
})
export class SwalService {
  constructor() {
  }

  public success(title: string, text: string, icon: string, timer: number): Promise<SweetAlertResult> {
    return Swal.fire({icon: 'success', title: title, text: text, timer: timer});
  }

}
