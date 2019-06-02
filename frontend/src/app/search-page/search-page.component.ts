import { Component, OnInit } from '@angular/core';
import { IBook } from "../interfaces";
import { BookService } from "../book-service/book.service";
import {UserService} from "../user-service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.scss']
})
export class SearchPageComponent implements OnInit {

  books: Array<IBook>;
  isLoading: boolean;
  isLoading2: Array<boolean>;
  successLoading: Array<boolean>;
  isChecked: Array<boolean>;
  book: IBook;

  constructor(private bookService: BookService, private userService: UserService, private router: Router) {
    this.isLoading = false;
    this.isLoading2 = [];
    this.successLoading = [];
    this.isChecked = [];
    this.books = [];
  }

  ngOnInit() {
  }

  onSearch(event: any)
  {
    if(event.target.value.length > 0)
    {
      this.isLoading = true;
      this.books.length = 0;
      this.bookService.searchBooks(event.target.value).subscribe(data => this.books = data,
          error1 => error1,
        () => {
          this.isLoading = false;
          if(this.books != null)
          {
            for(let i = 0; i < this.books.length; i++)
              this.isLoading2.push(false);
            for(let i = 0; i < this.books.length; i++)
              this.isChecked.push(false);
            for(let i = 0; i < this.books.length; i++)
              this.successLoading.push(false);
          }

        });
    }

  }

  onAdd(event: any)
  {
    if(this.userService.isLoggedOut())
      this.router.navigate(['/login']);
    else{
      if(event.target.textContent !== "Added to library")
      {
        this.isLoading2[event.target.parentElement.id] = true;
        this.bookService.getBookDetails(this.books[event.target.parentElement.id].publisher).subscribe(data => this.book = data,
            error1 => error1,
          () => {
              this.bookService.addBook(this.book).subscribe(() => {},
                error1 => {
                  this.isLoading2[event.target.parentElement.id] = false;
                  this.successLoading[event.target.parentElement.id] = false;
                  this.isChecked[event.target.parentElement.id] = true;
                },
                () => {
                  this.isLoading2[event.target.parentElement.id] = false;
                  this.successLoading[event.target.parentElement.id] = true;
                  this.isChecked[event.target.parentElement.id] = true;
                });
          });
      }
    }
  }

}
