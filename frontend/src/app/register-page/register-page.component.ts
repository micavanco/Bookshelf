import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import {IUser} from "../interfaces";
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  user: IUser;


  constructor(private router: Router, private route: ActivatedRoute) {
    this.user = {
      user_id: 12,
      username: "anonymous",
      password: 'password',
      enabled: true,
      authority: 'authorities'
    }as IUser
  }

  ngOnInit() {
  }

  onCreate(){
    this.router.navigate(['']);
  }

}
