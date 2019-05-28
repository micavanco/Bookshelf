import { Component, OnInit } from '@angular/core';
import { UserService } from "../user-service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-account-page',
  templateUrl: './account-page.component.html',
  styleUrls: ['./account-page.component.scss']
})
export class AccountPageComponent implements OnInit {

  username: string;


  constructor(private userService: UserService, private router: Router) {
    this.username = localStorage.getItem('username');
    if(!userService.isLoggedIn())
      this.router.navigate(['/login']);
  }

  ngOnInit() {
  }

  onLogout()
  {
    this.userService.logout();
    this.router.navigate(['/login']);
  }

}
