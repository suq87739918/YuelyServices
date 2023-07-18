# URL Shortener Service

This project is a URL shortener service, similar in functionality to bitly.com. It takes in long URLs and returns a shortened version that is easier to share.

## Technologies Used

1. **Language:** Java
2. **Framework:** Spring Boot
3. **Database:** MySQL, Redis
4. **Database Version Control:** 
5. **API:** RESTful

## Features

The service aims to create a more manageable and shareable URL from a given long URL. It uses a combination of MySQL and Redis databases to store and retrieve data, respectively. We have also implemented a database version control to handle database versioning. This service also validates the input URL for its validity. 

The short URL is generated using a custom algorithm implemented in the `Encoder` class. This algorithm generates a random 7-character long string using a mix of 62 different characters. The `UrlService` class is responsible for handling the logic for saving and retrieving URLs from both the MySQL database and Redis cache.


