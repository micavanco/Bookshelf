import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import { Injectable } from '@angular/core';
import { IUser } from "../interfaces";
import { environment } from '../../environments/environment'
import {Observable, ObservableInput} from "rxjs";
import { map } from 'rxjs/operators'
import 'rxjs/add/operator/do'
import 'rxjs/add/operator/shareReplay'
import 'rxjs/add/operator/catch'


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

  login(username: string, password: string)
  {
    return this.httpClient.post(environment.baseUrl+'/v1/users/login',{username, password})
      .do(res => this.setSession(res))
      .shareReplay();
  }

  setSession(authResult)
  {
    let date = new Date().getTime()+parseInt(authResult.expires_at)*60000;
    localStorage.setItem('id_token', authResult.token);
    localStorage.setItem('username', authResult.username);
    localStorage.setItem('expires_at', JSON.stringify(date));
  }

  logout()
  {
    localStorage.removeItem('id_token');
    localStorage.removeItem('username');
    localStorage.removeItem('expires_at');
  }

  public isLoggedIn()
  {
    return parseInt(localStorage.getItem('expires_at'))-new Date().getTime() >= 0 ? true : false;
  }

  isLoggedOut()
  {
    return !this.isLoggedIn();
  }

  getExpiration()
  {
    const expiration = localStorage.getItem('expires_at');
    const expires_at = JSON.parse(expiration);
    //return moment(expires_at);
  }
}
