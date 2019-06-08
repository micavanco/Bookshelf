import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserService } from "./user-service/user.service";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { UserBoxComponent } from './user-box/user-box.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { BottomBarComponent } from './bottom-bar/bottom-bar.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { LibraryPageComponent } from './library-page/library-page.component';
import { SearchPageComponent } from './search-page/search-page.component';
import { AuthInterceptorService } from "./auth-interceptor/auth-interceptor.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { AccountPageComponent } from './account-page/account-page.component';
import { BookService } from "./book-service/book.service";


@NgModule({
  declarations: [
    AppComponent,
    UserBoxComponent,
    NavBarComponent,
    BottomBarComponent,
    LoginPageComponent,
    RegisterPageComponent,
    LibraryPageComponent,
    SearchPageComponent,
    AccountPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [UserService, {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptorService,
    multi: true,
  }, BookService],
  bootstrap: [AppComponent]
})
export class AppModule { }
