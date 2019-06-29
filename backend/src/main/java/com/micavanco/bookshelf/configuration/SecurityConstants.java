package com.micavanco.bookshelf.configuration;

public class SecurityConstants {

    public static final String SIGN_UP_URL = "/v1/users/add";
    public static final String SIGN_IN_URL = "/v1/users/login";
    public static final String SEARCH_URL = "/v1/books/search";
    public static final String ADD_BOOK_URL = "/v1/books/add";
    public static final String DELETE_BOOK_URL = "/v1/books/delete";
    public static final String ADMIN_BOOKS_URL_USER = "/v1/books/user";
    public static final String ADMIN_USERS_URL_DELETE = "/v1/users/delete";
    public static final String SECRET_KEY = "secretAPIKey";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long   TOKEN_EXPIRATION_TIME = 600_000;
    public static final String AUTHORITIES_KEY = "scopes";
}
