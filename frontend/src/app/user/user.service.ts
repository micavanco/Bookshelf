import { HttpClient} from "@angular/common/http";
import { Injectable } from '@angular/core';
import {IUser} from "../interfaces";
import { environment } from '../../environments/environment'
import {Observable} from "rxjs";
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  private transformToIUser(data: Object): IUser
  {
    return {
      user_id: data['id'],
      username: data['username'],
      password: data['password'],
      enabled: data['enabled'],
      authority: data['authorities'][0].authority
    };
  }

  getUser(username: string): Observable<IUser>
  {
    return this.httpClient.get(
      environment.baseUrl+'/v1/users/get?username='+username
    ).pipe(map(data => this.transformToIUser(data)));
  }
}
