# Bookshelf
## Description
Web application to store books and monitor current reading progress. Newly created user has it's own library in PostgreSQL database. Backend was written in Java with Spring framework and Hibernate framework to manage database. Frontend was written in Angular. Books descriptions are scraped from Amazon website based on search URL.

On Library page user pages already done and pages left to read are automatically summed up for all books. User can choose deadline for reading all books and based on those informations are calculated number of pages required to read per day in order to finish books in appointed date.

On a main page user can type title of a book that is looking for and accordingly to results add certain books. There is built in validation mechanism to avoid adding the same book more than once.

In order to get access to all features is required to create an account and be logged in. User is validated by JSON Web Tokens received from backend. API endpoints are secured and available relatively to user role. Search endpoint is available without being logged in. 

## Screenshots
### Search books page
![alt text](https://raw.githubusercontent.com/micavanco/Bookshelf/master/screenshots/search_page.png)
### User library page
![alt text](https://raw.githubusercontent.com/micavanco/Bookshelf/master/screenshots/library_page.png)
### Login User page
![alt text](https://raw.githubusercontent.com/micavanco/Bookshelf/master/screenshots/login_page.png)
#### Login page loading
![alt text](https://raw.githubusercontent.com/micavanco/Bookshelf/master/screenshots/login_page_loading.png)
### User account page
![alt text](https://raw.githubusercontent.com/micavanco/Bookshelf/master/screenshots/account_page.png)
### Register page
![alt text](https://raw.githubusercontent.com/micavanco/Bookshelf/master/screenshots/register_page.png)