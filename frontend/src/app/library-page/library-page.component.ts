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
  pages: number;
  pages_done: number;
  start_day: any;
  end_day: any;
  per_day: number;
  per_month: number;
  sort_value: string;

  constructor(private bookService: BookService) {
    this.isEditable = [];
    this.pages = 0;
    this.per_day = 0;
    this.per_month = 0;
    this.pages_done = 0;
    this.sort_value = 'title';
    this.start_day = new Date().toISOString().split('T')[0];
    let date = new Date();
    date.setDate(new Date().getDate()+1);
    this.end_day = date.toISOString().split('T')[0];
  }

  ngOnInit() {
    this.bookService.getUserBooks().subscribe(data => this.books = data,
      error1 => error1,
      () => {
        if(this.books != null)
        {
          let pages = 0, pages_done = 0;
          for(let i = 0; i < this.books.length; i++)
          {
            this.isEditable.push(false);
            pages += this.books[i].pages;
            pages_done += this.books[i].pages_done;
          }
          this.pages = pages;
          this.pages_done = pages_done;
          this.updatePagesCalc();
        }


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
    if(Number(e.target.value) >= 0 && Number(e.target.value) <= this.books[e.target.parentElement.id].pages)
    {
      this.isEditable[e.target.parentElement.id] = false;
      this.books[e.target.parentElement.id].pages_done = Number(e.target.value);
      this.bookService.updateBook(this.books[e.target.parentElement.id]).subscribe(()=>{},
        error1 => error1,
        () => this.ngOnInit()
      );
    }
  }

  onStartChanged(date): any
  {
    let temp_date = new Date(date.target.value);
    if( temp_date.getTime() < new Date().getTime())
    {
      let temp = new Date().toISOString().split('T')[0];
      this.updateDate(temp);
      return temp;
    }
    else
    {
      let temp = temp_date.toISOString().split('T')[0];
      this.updateDate(temp);
      return temp;
    }
  }

  onEndChanged(date): any
  {
    let input = new Date(date.target.value).getTime();
    let curr_date = new Date(this.start_day).getTime();
    if(input <= curr_date)
    {
      this.per_day = this.pages_done;
      let temp = new Date(this.start_day);
      temp.setDate(temp.getDate()+1);
      return temp.toISOString().split('T')[0];
    }
    else
    {
      this.updatePagesCalc();
      return  date.target.value;
    }
  }

  updatePagesCalc()
  {
    this.per_day = Math.ceil((this.pages-this.pages_done)/((new Date(this.end_day).getTime() - new Date(this.start_day).getTime())/1000/60/60/24));
    this.per_month = this.per_day*30;
  }

  updateDate(date)
  {
      let temp = new Date(date);
      temp.setDate(temp.getDate()+1);
      this.end_day =  temp.toISOString().split('T')[0];
  }

  onClickDeleteButton(title)
  {
    this.bookService.deleteBook(title).subscribe(()=>{}
    ,error1 => {}
    ,() => {
        let amount = Number(localStorage.getItem('amount_books'));
        localStorage.setItem('amount_books', String(--amount));
        this.books = this.books.filter(book => book.title !== title);
      })
  }

}
