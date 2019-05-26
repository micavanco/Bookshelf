import { Component, OnInit } from '@angular/core';
import { IUser } from "../interfaces";
import { UserService } from "../user-service/user.service";


@Component({
  selector: 'app-user-box',
  templateUrl: './user-box.component.html',
  styleUrls: ['./user-box.component.scss']
})
export class UserBoxComponent{

  user: IUser;
  expires_at: string;

  constructor(private userService: UserService) {
    localStorage.setItem('expires_at', '');
    this.user = {
      user_id: 12,
      username: "anonymous",
      password: 'password',
      enabled: true,
      authority: 'authorities'
    }as IUser
  }

  getUsername()
  {
    return localStorage.getItem('username');
  }

  getExpires()
  {
    return ((parseInt(localStorage.getItem('expires_at'))-new Date().getTime())/60000).toFixed(1);
  }

}
