import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import {IUser} from "../interfaces";
import {FormBuilder, FormGroup, FormsModule, Validators} from '@angular/forms';
import { UserService } from "../user-service/user.service";
import {first} from "rxjs/operators";


@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  form : FormGroup;
  isPassTheSame: boolean;
  isPassCorrect: boolean;
  userExists: boolean;


  constructor(private userService: UserService, private router: Router, private fb:FormBuilder, private route: ActivatedRoute) {
    this.isPassTheSame = true;
    this.isPassCorrect = true;
    this.userExists = false;
    this.form = this.fb.group({
      username: ['',Validators.required],
      password: ['',Validators.required],
      confirmPassword: ['',Validators.required]
    });
  }

  ngOnInit() {
  }

  onCreate(){
    const val = this.form.value;
    if(val.password === val.confirmPassword)
    {
      this.isPassTheSame = true;
      if(val.password.length > 5)
      {
        this.isPassCorrect = true;
        this.userService.createUser(val.username, val.password, val.password)
          .subscribe(data => {
              this.userExists = false;
              this.router.navigate(['/login']);
            },
            error => this.userExists = true);
      }else
        this.isPassCorrect = false;
    }else
      this.isPassTheSame = false;

  }

}
