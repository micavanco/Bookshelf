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
        if(this.books != null)
          for(let i = 0; i < this.books.length; i++)
            this.isEditable.push(false)
      }
        );

  }

  onAddPages(e: any)
  {
    this.isEditable[e.target.id] = true;
  }

  onAddPagesNumber(e: any)
  {
    this.isEditable[e.target.parentElement.id] = true;
  }

  onInputBlur(e: any)
  {
    if(Number(e.target.value) >= 0 && Number(e.target.value) <= this.books[e.target.parentElement.id].pages_done)
    {
      this.isEditable[e.target.parentElement.id] = false;
      this.books[e.target.parentElement.id].pages_done = Number(e.target.value);
      this.bookService.updateBook(this.books[e.target.parentElement.id]).subscribe(()=>{},
        error1 => error1,
        () => this.ngOnInit()
      );
    }
  }

}
