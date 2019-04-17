package com.micavanco.bookshelf.model;


import javax.persistence.*;

@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Book book;



}
