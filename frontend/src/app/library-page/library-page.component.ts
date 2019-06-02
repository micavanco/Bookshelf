import { Component, OnInit } from '@angular/core';
import {BookService} from "../book-service/book.service";
import {IBook} from "../interfaces";

@Component({
  selector: 'app-library-page',
  templateUrl: './library-page.component.html',
  styleUrls: ['./library-page.component.scss']
})
export class LibraryPageComponent implements OnInit {

  books: Array<IBook>;
  isEditable: Array<boolean>;

  constructor(private bookService: BookService) {
    this.isEditable = [];
  }

  ngOnInit() {
    this.bookService.getUserBooks().subscribe(data => this.books = data,
      error1 => error1,
      () => {
        for(let i = 0; i < this.books.length; i++)
          this.isEditable.push(false)
      }
        );

  }

  onAddPages(e: any)
  {
    this.isEditable[e.target.id] = true;
  }

  onInputBlur(e: any)
  {
    this.isEditable[e.target.parentElement.id] = false;
  }

}
