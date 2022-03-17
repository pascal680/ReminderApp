import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn:"root"
})
export class BaseService{
  protected readonly apiGatewayUrl= "http://127.0.0.1:9595"


}
