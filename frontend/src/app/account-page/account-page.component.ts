import { Component, OnInit } from '@angular/core';
import { UserService } from "../user-service/user.service";
import {Router} from "@angular/router";
import * as jwt_decode from "jwt-decode";

@Component({
  selector: 'app-account-page',
  templateUrl: './account-page.component.html',
  styleUrls: ['./account-page.component.scss']
})
export class AccountPageComponent implements OnInit {

  username: string;
  amount_books: string;


  constructor(private userService: UserService, private router: Router) {
    this.username = localStorage.getItem('username');
    this.amount_books = localStorage.getItem('amount_books');
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

  onDeleteUser()
  {
    this.userService.deleteUser(localStorage.getItem('username'), jwt_decode(localStorage.getItem('id_token')).password)
      .subscribe(() => {
        this.userService.logout();
        this.router.navigate(['/login']);
      });
  }

}
