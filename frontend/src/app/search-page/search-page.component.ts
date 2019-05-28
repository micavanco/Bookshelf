import { Component, OnInit } from '@angular/core';
import { IBook } from "../interfaces";
import { BookService } from "../book-service/book.service";

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.scss']
})
export class SearchPageComponent implements OnInit {

  books: Array<IBook>;

  constructor(private bookService: BookService) { }

  ngOnInit() {
  }

  onSearch(event: any)
  {
    if(event.target.value.length > 0)
      this.bookService.searchBooks(event.target.value).subscribe(data => this.books = data);
  }

}
