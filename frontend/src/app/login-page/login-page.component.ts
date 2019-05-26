import { Component, OnInit } from '@angular/core';
import { UserService } from "../user-service/user.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { UserBoxComponent } from "../user-box/user-box.component";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {

  form : FormGroup;
  error: boolean;

  constructor(private userService: UserService, private fb:FormBuilder, private router: Router) {
    this.error = false;
    this.form = this.fb.group({
      username: ['',Validators.required],
      password: ['',Validators.required]
    });
  }

  login()
  {
    const val = this.form.value;
    if(val.username && val.password)
    {
      this.userService.login(val.username, val.password).subscribe(()=>{
        this.error = false;
        this.router.navigateByUrl('/');
        },
        error => this.error = true);
    }
  }

}
