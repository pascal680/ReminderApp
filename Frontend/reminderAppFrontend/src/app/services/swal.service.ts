import {Injectable} from "@angular/core";
import Swal, {SweetAlertIcon, SweetAlertResult} from "sweetalert2";

Injectable({
  providedIn: 'root',
})
export class SwalService {
  constructor() {
  }

  public timedAlertSuccess(title: string, text: string, timer: number = 3000): Promise<SweetAlertResult> {
    return Swal.fire({icon: "success", title: title, text: text, timer: timer, showConfirmButton: false});
  }

  public timedAlertError(title: string, text: string, timer: number = 3000): Promise<SweetAlertResult> {
    return Swal.fire({icon: "error", title: title, text: text, timer: timer, showConfirmButton: false});
  }

  public toastAlert(title: string, text: string, icon: SweetAlertIcon): Promise<SweetAlertResult> {
    return Swal.fire({icon: icon, title: title, text: text, showConfirmButton: false});
  }

}
