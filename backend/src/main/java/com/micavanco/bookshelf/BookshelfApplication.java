package com.micavanco.bookshelf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BookshelfApplication {
    private static final Logger logger = LoggerFactory.getLogger(BookshelfApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(BookshelfApplication.class, args);
        logger.info("Hello world from logger");
    }

}
