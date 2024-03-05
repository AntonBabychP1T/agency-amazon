# Introduction
### The Amazon Sales and Traffic Statistics API is a Spring Boot RESTful service designed to manage and provide comprehensive sales and traffic statistics for Amazon sellers. It's a powerful tool for analyzing detailed metrics about products sold on Amazon, offering insights into sales, traffic, and overall performance. The API uses MongoDB for data storage and Docker for containerization, ensuring scalability and ease of deployment.

## Features
Registration and Authentication: Secure endpoints for user registration and login, facilitating access control to the API using JWT token.

Statistics Management: Endpoints to retrieve detailed sales and traffic statistics based on date ranges or specific Amazon Standard Identification Numbers (ASINs).

Comprehensive Data Analysis: Capability to aggregate and calculate total statistics for all dates and all ASINs, providing a broad view of sales and traffic performance.

MongoDB Integration: Efficient and scalable data management with MongoDB, offering robust handling of large datasets.

Docker Support: Easy deployment and scaling using Docker containers, ensuring consistency across different environments.

## Endpoints
- Authentication Manager
POST /v1/auth/registration

*Example of request body*

```json
{
  "email": "email@mail.com",
  "password": "Min8Character",
}
```

Summary: Endpoint for new user registration.
Input: User registration details.
Output: UserResponseDto containing user details.

- POST /v1/auth/login

Summary: Endpoint for user login to obtain a JWT token.
Input: UserLoginRequestDto containing email and password.
Output: UserLoginResponseDto containing the JWT token.

## Statistics Manager
- GET /v1/stats/by-date

Summary: Get statistics for a specific date range.
Input: startDate and endDate (format: yyyy-MM-dd).
Output: TotalSalesAndTrafficStatistics for the specified date range.

- GET /v1/stats/by-asin

Summary: Get statistics for specified ASINs.
Input: List of ASIN strings.
Output: TotalSalesAndTrafficStatisticsByAsin for the specified ASINs.

- GET /v1/stats/total-by-dates

Summary: Get total statistics across all dates.
Output: TotalSalesAndTrafficStatistics encompassing all dates.

- GET /v1/stats/total-by-asins

Summary: Get total statistics across all ASINs.
Output: TotalSalesAndTrafficStatisticsByAsin encompassing all ASINs.
Usage

This API is particularly useful for Amazon sellers, business analysts, and marketing teams who require detailed insights into their product's performance on Amazon.
The comprehensive data provided can aid in making informed decisions, strategizing marketing efforts, and identifying areas for improvement in sales tactics.

Technology Stack
Spring Web: For creating the RESTful services.
Spring Security: For login and authentication.
Swagger: For API documentation. 
MongoDB: As the database for storing and managing large sets of data.
Docker: For containerizing the application, ensuring easy deployment and scalability.
