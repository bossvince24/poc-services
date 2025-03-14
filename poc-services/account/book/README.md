Task: Implement a GraphQL API for a Bookstore

* Objective
  
- Create a GraphQL API in a Spring Boot application to manage books and authors. The API should allow users to:
- Query books and authors
- Mutate (add/update) books and authors
- Implement pagination and filtering

* Requirements

- Spring Boot Setup
- Use Spring Boot with Spring GraphQL
- Use an in-memory H2 database or PostgreSQL
- Use JPA (Hibernate) for database interactions
- GraphQL Schema

* Define GraphQL schema for Book and Author
- A book should have:
id: ID!
title: String!
author: Author
publishedYear: Int
- An author should have:
id: ID!
name: String!
books: [Book]

* Queries
- Fetch all books
- Fetch a book by ID
- Fetch all authors

* Mutations
- Add a new book
- Update book details
- Delete a book
- Filters & Pagination

Filter books by author or publishedYear
Implement pagination using GraphQL Cursor-based Pagination
Security (Optional, Bonus)

Add authentication using Spring Security + JWT
Restrict mutations to authenticated users
