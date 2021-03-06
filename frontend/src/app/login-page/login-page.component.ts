import { Component, OnInit } from '@angular/core';
import { UserService } from "../user-service/user.service";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { UserBoxComponent } from "../user-box/user-box.component";
import { BookService } from "../book-service/book.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {

  form : FormGroup;
  error: boolean;
  isLoading: boolean;

  constructor(private userService: UserService, private fb:FormBuilder, private router: Router, private bookService: BookService) {
    this.error = false;
    this.isLoading = false;
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
      this.isLoading = true;
      this.userService.login(val.username, val.password).subscribe(()=>{},
        error => {
          this.error = true;
          this.isLoading = false;
        },
        () => {
          this.error = false;
          this.isLoading = false;
          this.router.navigateByUrl('/');
          this.bookService.getUserAmountOfUserBooks().subscribe(data => localStorage.setItem("amount_books", data));
        });
    }
  }

}
