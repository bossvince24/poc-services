package com.example.book.service;

import java.util.List;

import com.example.book.entity.Book;
import com.example.book.exception.BookNotFoundException;
import com.example.book.response.DeleteBookResponse;

public interface BookService {
	
	List<Book> fetchAllBooks();
	
	List<Book> getAllBooks(Long authorId, Integer publishedYear, int page, int size);
	
	Book fetchBookById(Long id) throws BookNotFoundException; 
	
	Book addNewBook(Book book, Long id);

	Book updateBookDetails(Book book, Long id) throws BookNotFoundException;
	
	DeleteBookResponse deleteABook(Long id);
	
}
