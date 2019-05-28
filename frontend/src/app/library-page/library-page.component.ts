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

  constructor(private bookService: BookService) { }

  ngOnInit() {
    this.bookService.getUserBooks().subscribe(data => this.books = data);
  }

}
