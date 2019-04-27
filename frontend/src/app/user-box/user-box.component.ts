import { Component, OnInit } from '@angular/core';
import { UserService } from "../user/user.service";
import {IUser} from "../interfaces";

@Component({
  selector: 'app-user-box',
  templateUrl: './user-box.component.html',
  styleUrls: ['./user-box.component.scss']
})
export class UserBoxComponent implements OnInit {

  user: IUser;

  constructor(private userService: UserService) {
    this.user = {
      user_id: 12,
      username: "witam",
      password: 'password',
      enabled: true,
      authority: 'authorities'
    }as IUser
  }

  ngOnInit() {
    this.userService.getUser('micavanco39').subscribe((data: IUser) => this.user = data);
  }

}
