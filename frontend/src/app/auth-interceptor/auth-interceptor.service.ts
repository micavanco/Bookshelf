import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = localStorage.getItem("id_token");

    if(token)
    {
      const cloned = req.clone({
        headers: req.headers.set("Authorization", token)
      });
      return next.handle(cloned);
    }else
    {
      return next.handle(req);
    }
  }
}
