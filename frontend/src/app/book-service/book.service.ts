import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { IBook} from "../interfaces";
import { environment} from "../../environments/environment";
import * as jwt_decode from "jwt-decode";


@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }

  addBook(book: IBook)
  {
    return this.httpClient.post(environment.baseUrl+'/v1/books/add', {book, user_id: jwt_decode(localStorage.getItem('id_token')).user_id,
      user_password: jwt_decode(localStorage.getItem('id_token')).password});
  }

  deleteBook(title: string)
  {
    return this.httpClient.delete(environment.baseUrl+'/v1/books/delete?user_id='+jwt_decode(localStorage.getItem('id_token')).user_id
      +"&title="+title
      +"&user_password="+jwt_decode(localStorage.getItem('id_token')).password);
  }

  updateBook(book: IBook)
  {
    return this.httpClient.post(environment.baseUrl+'/v1/books/update', {book, user_id: jwt_decode(localStorage.getItem('id_token')).user_id,
      user_password: jwt_decode(localStorage.getItem('id_token')).password});
  }

  getUserBooks()
  {
    return this.httpClient.get<Array<IBook>>(environment.baseUrl+'/v1/books/user?username='+localStorage.getItem('username')+
      "&user_password="+jwt_decode(localStorage.getItem('id_token')).password);
  }

  searchBooks(title: string)
  {
    return this.httpClient.get<Array<IBook>>(environment.baseUrl+'/v1/books/search?title='+title);
  }

  getBookDetails(url: string)
  {
    return this.httpClient.post<IBook>(environment.baseUrl+'/v1/books/getBookDetails', url);
  }

  getUserAmountOfUserBooks()
  {
    return this.httpClient.get<string>(environment.baseUrl+'/v1/books/user/amount?username='+localStorage.getItem('username')+
      "&user_password="+jwt_decode(localStorage.getItem('id_token')).password);
  }
}
