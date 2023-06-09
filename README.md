Movie Collection App

Made using:
- Spring Boot
- Spring JPA
- Thymeleaf
- Bootstrap

Information about the project:

A web page - movie database, where users can choose a Movie to watch, considering Movie ratings and reviews. Users can also register and login, rate Movies and write their own reviews. No actual Movies stored, just Movie’s information. Movie rating is calculated based on users' reviews.
Administrator can login and update Movie database through web page: add, delete, update Movies. 
Example of existing similar solution: IMDb.

- Registration with admin role available via Postman, users register using the web app. 
- You need a strong password to sign up for the app.
- Accounts with the admin role can add, update, or delete movies.
- Users can rate movies and write reviews, also search movies and see movie details. 
- Search and movie details can be viewed without logging into the application. 
- Writing reviews is only available for registered users. 

How to run the application?

Change database details from application.properties (default database management system :mysql)
- database name: movieCollection
- spring.datasource.url = jdbc:mysql://localhost:3306/movieCollection?serverTimezone=UTC&createDatabaseIfNotExist=true
- spring.datasource.username = root (change if necessary)
- spring.datasource.password = root (change if necessary)

Run the application, then open your browser and launch it at 'localhost' as the URL.
