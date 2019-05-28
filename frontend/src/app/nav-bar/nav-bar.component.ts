import { Component, OnInit } from '@angular/core';
import { UserService } from "../user-service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
  }

  isLoggedIn()
  {
    if(!this.userService.isLoggedIn())
      this.router.navigateByUrl('/login')
    else
      this.router.navigateByUrl('/library')
  }

}
