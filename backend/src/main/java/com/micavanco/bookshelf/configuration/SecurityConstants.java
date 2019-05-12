package com.micavanco.bookshelf.configuration;

public class SecurityConstants {

    public static final String SIGN_UP_URL = "/v1/users/add";
    public static final String SIGN_IN_URL = "/v1/users/login";
    public static final String ADD_BOOK_URL = "/v1/books/add";
    public static final String DELETE_BOOK_URL = "/v1/books/delete";
    public static final String ADMIN_BOOKS_URL = "/v1/books/**";
    public static final String ADMIN_USERS_URL = "/v1/users/**";
}
