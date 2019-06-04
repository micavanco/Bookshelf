import { Component, OnInit } from '@angular/core';
import { UserService } from "../user-service/user.service";
import { Router } from "@angular/router";
import * as $ from 'jquery';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  i:number;

  constructor(private userService: UserService, private router: Router) {
    this.i = 0;
  }

  ngOnInit() {
    this.loadingDots();
  }

  isLoggedIn()
  {
    if(!this.userService.isLoggedIn())
      this.router.navigateByUrl('/login')
    else
      this.router.navigateByUrl('/library')
  }

  loadingDots()
  {
    $(".dot1").animate({opacity: String(this.i/10)}, 100);
    $(".dot2").animate({opacity: String(1-this.i/10)}, 100);
    $(".dot3").animate({opacity: String(this.i/10)}, 100);
    if(this.i == 10)
      this.i=0;
    this.i++;

    setTimeout(()=>this.loadingDots(), 100);
  }


}
