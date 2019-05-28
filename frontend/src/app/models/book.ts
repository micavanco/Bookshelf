import {IBook} from "../interfaces";

export class Book implements IBook{
  title: string;
  author: string;
  year: number;
  pages: number;
  language: string;
  publisher: string;
  cover: string;
  pages_done: number;


  constructor(title: string, author: string, year: number, pages: number, language: string, publisher: string, cover: string, pages_done: number) {
    this.title = title;
    this.author = author;
    this.year = year;
    this.pages = pages;
    this.language = language;
    this.publisher = publisher;
    this.cover = cover;
    this.pages_done = pages_done;
  }
}
