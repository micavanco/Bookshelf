import { Component, OnInit } from '@angular/core';
import {IBook} from "../../interfaces";

@Component({
  selector: 'app-result-box',
  templateUrl: './result-box.component.html',
  styleUrls: ['./result-box.component.scss']
})
export class ResultBoxComponent implements OnInit {

  book: IBook;

  constructor() {
    this.book = {
      title: "Java",
      author: "ziom",
      year: 1992,
      pages: 234,
      language: "English",
      publisher: "Packt",
      cover: "assets/img/java.jpg"
    } as IBook;
  }

  ngOnInit() {
  }

}
