package com.example.book.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.exception.BookNotFoundException;
import com.example.book.response.DeleteBookResponse;
import com.example.book.serviceImpl.AuthorServiceImpl;
import com.example.book.serviceImpl.BookServiceImpl;

@Controller
public class BookResolver {

	@Autowired
	private BookServiceImpl bookService;
	
	@Autowired
	private AuthorServiceImpl authorService;
	
	@MutationMapping
	public Book addNewBook(@Argument String title, @Argument Long authorId, @Argument int publishedYear) {
		
		Book book = new Book();
		book.setTitle(title);
		book.setPublishedYear(publishedYear);
		
		return bookService.addNewBook(book, authorId);
	}
	
	@QueryMapping
	public List<Book> fetchAllBooks() {
		List<Book> books = bookService.fetchAllBooks();
		
		if (books.isEmpty()) {
			throw new BookNotFoundException("No book found.");
		}
		return books;
	}
	
	@QueryMapping
	public Book fetchBookById(@Argument Long bookId) {
		return bookService.fetchBookById(bookId);
	}
	
	@MutationMapping
	public Book updateBookDetails(@Argument Long id,  @Argument String title, 
			@Argument Long authorId, @Argument int publishedYear) {
		
		Book existingBook = bookService.fetchBookById(id);
		
		if (existingBook == null) {
			throw new BookNotFoundException("Book with ID " + id + " not found");
		}
		
		Author author = authorService.getAuthorById(authorId);
		if (author == null) {
			throw new BookNotFoundException("Author with ID " + authorId + " not found");
		}
		
//		if (authorName != null && !authorName.isEmpty()) {
//			author.setName(authorName);
//			authorService.addAuthor(author);
//		}
		existingBook.setTitle(title);
		existingBook.setPublishedYear(publishedYear);
		existingBook.setAuthor(author);
		
		return bookService.updateBookDetails(existingBook, id);
	}
	
	@MutationMapping
	public DeleteBookResponse deleteABook(@Argument Long id) {
		return bookService.deleteABook(id);
	}
	
	@QueryMapping
	public List<Book> getAllBooks(@Argument Long authorId, @Argument Integer publishedYear, 
			@Argument int page, @Argument int size) {
		return bookService.getAllBooks(authorId, publishedYear, page, size);
	}
}
