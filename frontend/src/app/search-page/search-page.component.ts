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
  book: IBook;

  constructor(private bookService: BookService, private userService: UserService, private router: Router) {
    this.isLoading = false;
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
        () => this.isLoading = false);
    }

  }

  onAdd(event: any)
  {
    if(this.userService.isLoggedOut())
      this.router.navigate(['/login']);
    else{
      if(event.target.textContent !== "Added to library")
      {
        event.target.textContent = "Added to library";
        event.target.parentElement.style.background = "rgba(156,156,156,0.51)";
        this.bookService.getBookDetails(this.books[event.target.id].publisher).subscribe(data => this.book = data,
            error1 => error1,
          () => this.bookService.addBook(this.book).subscribe());
      }
    }
  }

}
