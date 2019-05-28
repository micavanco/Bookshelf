import {IBook} from "../interfaces";

export class Book implements IBook{
  author: string;
  cover: string;
  language: string;
  pages: number;
  publisher: string;
  title: string;
  year: number;

  constructor(author: string, cover: string, language: string, pages: number, publisher: string, title: string, year: number) {
    this.author = author;
    this.cover = cover;
    this.language = language;
    this.pages = pages;
    this.publisher = publisher;
    this.title = title;
    this.year = year;
  }
}
