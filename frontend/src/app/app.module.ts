import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchBoxComponent } from './search-page/search-box/search-box.component';
import { ResultBoxComponent } from './search-page/result-box/result-box.component';
import { UserService } from "./user-service/user.service";
import { HttpClientModule } from "@angular/common/http";
import { UserBoxComponent } from './user-box/user-box.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { BottomBarComponent } from './bottom-bar/bottom-bar.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { LibraryPageComponent } from './library-page/library-page.component';
import { SearchPageComponent } from './search-page/search-page.component';
import {AuthInterceptorService} from "./auth-interceptor/auth-interceptor.service";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    SearchBoxComponent,
    ResultBoxComponent,
    UserBoxComponent,
    NavBarComponent,
    BottomBarComponent,
    LoginPageComponent,
    RegisterPageComponent,
    LibraryPageComponent,
    SearchPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [UserService, AuthInterceptorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
