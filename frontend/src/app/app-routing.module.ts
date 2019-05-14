import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginPageComponent } from "./login-page/login-page.component";
import { LibraryPageComponent } from "./library-page/library-page.component";
import { SearchPageComponent } from "./search-page/search-page.component";
import { RegisterPageComponent } from "./register-page/register-page.component";

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'library', component: LibraryPageComponent},
  { path: '', component: SearchPageComponent},
  { path: 'register', component: RegisterPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
