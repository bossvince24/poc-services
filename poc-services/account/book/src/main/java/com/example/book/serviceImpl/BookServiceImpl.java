package com.example.book.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.exception.BookNotFoundException;
import com.example.book.repository.AuthorRepository;
import com.example.book.repository.BookRepository;
import com.example.book.response.DeleteBookResponse;
import com.example.book.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public List<Book> fetchAllBooks() {
		// TODO Auto-generated method stub
		return bookRepository.findAll();
	}

	@Override
	public Book fetchBookById(Long id) throws BookNotFoundException {
		// TODO Auto-generated method stub
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No book found with id: " + id));
	}

	@Override
	public Book addNewBook(Book book, Long authorId) {
		// TODO Auto-generated method stub
		Author author = authorRepository.findById(authorId).orElseThrow(() -> new BookNotFoundException("Author Not Found."));
		
		book.setAuthor(author);
		
		return bookRepository.save(book);
	}

	@Override
	public Book updateBookDetails(Book book, Long id) throws BookNotFoundException {
		// TODO Auto-generated method stub
		
		Book existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book Not Found."));
		
		existingBook.setTitle(book.getTitle());
		existingBook.setPublishedYear(book.getPublishedYear());
		existingBook.setAuthor(book.getAuthor());
		
		return bookRepository.save(existingBook);
	}
		
//		return bookRepository.findById(id).map(existingBook -> { 
//			existingBook.setTitle(book.getTitle());
//			existingBook.setPublishedYear(book.getPublishedYear());
//			existingBook1.setAuthor(book.getAuthor());
//			
//		return bookRepository.save(existingBook);
//			}).orElseThrow(() -> new BookNotFoundException("Book Not Found with id: " + id));
//		}
	
//
	@Override
	public DeleteBookResponse deleteABook(Long id) {
		// TODO Auto-generated method stub
		
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found with id: " + id));
		
		bookRepository.delete(book);
		
		return new DeleteBookResponse(true, "Book Deleted Successfully");
		
	}

	@Override
	public List<Book> getAllBooks(Long authorId, Integer publishedYear, int page, int size) {
		// TODO Auto-generated method stub
		
		List<Book> books;
		
		if (authorId != null && publishedYear != null) {
			books = bookRepository.findByAuthorIdAndPublishedYear(authorId, publishedYear, PageRequest.of(page, size));
		} else if (authorId != null) {
			books = bookRepository.findByAuthorId(authorId, PageRequest.of(page, size));
		} else if (publishedYear != null) {
			books = bookRepository.findByPublishedYear(publishedYear, PageRequest.of(page, size));
		} else {
			books = bookRepository.findAll(PageRequest.of(page, size)).getContent();
		}
		
		if (books.isEmpty()) {
			throw new BookNotFoundException("No Books Found for the given Criteria");
		}
		
		return books;
	}

}
