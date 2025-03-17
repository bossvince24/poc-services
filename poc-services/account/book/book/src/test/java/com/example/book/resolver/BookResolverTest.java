package com.example.book.resolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.book.entity.Author;
import com.example.book.entity.Book;
import com.example.book.serviceImpl.BookServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class BookResolverTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private BookServiceImpl bookService;
	
	@DisplayName("Add New Book")
	@Test
	public void testAddNewBook() throws Exception {
		
		String query = "{ \"query\": \"mutation { addNewBook (title: \\\"GraphQL with Springboot\\\", authorId: 1, publishedYear: 2024) { bookId title author { name } publishedYear } }\" }";
		
		Author author = new Author();
		author.setName("John Doe");
		
		Book book = new Book();
		book.setBookId(1L);
		book.setTitle("GraphQL with Springboot");
		book.setPublishedYear(2024);
		book.setAuthor(author);
		
		when(bookService.addNewBook(any(Book.class), eq(1L))).thenReturn(book);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.addNewBook.bookId").value(book.getBookId()))
		.andExpect(jsonPath("$.data.addNewBook.title").value(book.getTitle()))
		.andExpect(jsonPath("$.data.addNewBook.publishedYear").value(book.getPublishedYear()))
		.andExpect(jsonPath("$.data.addNewBook.author.name").value(book.getAuthor().getName()));
		
		verify(bookService, times(1)).addNewBook(any(Book.class), eq(1L));
	}
	
	@DisplayName("Fetch All Books Test")
	@Test
	public void testFetchAllBooks() throws Exception {
		
		String query = "{ \"query\": \"query { fetchAllBooks { bookId title publishedYear } }\" }";
		
		Book books = new Book();
		books.setBookId(1L);
		books.setTitle("GraphQL with Springboot");
		books.setPublishedYear(2024);
		
		List<Book> bookList = Arrays.asList(books);
		
		when(bookService.fetchAllBooks()).thenReturn(bookList);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.fetchAllBooks[0].bookId").value(books.getBookId()))
		.andExpect(jsonPath("$.data.fetchAllBooks[0].title").value(books.getTitle()))
		.andExpect(jsonPath("$.data.fetchAllBooks[0].publishedYear").value(books.getPublishedYear()));
		
		verify(bookService, times(1)).fetchAllBooks();
	}
	
	@DisplayName("Fetch Book By Id")
	@Test
	public void testFetchBookById() throws Exception {
		
		String query = "{ \"query\": \"query { fetchBookById(bookId: 1) { bookId title author { id name } publishedYear } }\" }";
		
		Author author = new Author();
		author.setId(1L);
		author.setName("John Doe");
		
		Book book = new Book();
		book.setBookId(1L);
		book.setTitle("GraphQL with Springboot");
		book.setPublishedYear(2024);
		book.setAuthor(author);
		
		when(bookService.fetchBookById(1L)).thenReturn(book);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.fetchBookById.bookId").value(book.getBookId()))
		.andExpect(jsonPath("$.data.fetchBookById.title").value(book.getTitle()))
		.andExpect(jsonPath("$.data.fetchBookById.publishedYear").value(book.getPublishedYear()))
		.andExpect(jsonPath("$.data.fetchBookById.author.id").value(book.getAuthor().getId()))
		.andExpect(jsonPath("$.data.fetchBookById.author.name").value(book.getAuthor().getName()));
		
		verify(bookService, times(1)).fetchBookById(book.getBookId());
	}
	
	@DisplayName("Update Book Details")
	@Test
	public void testUpdateBookDetails() throws Exception {
		
		String query = "{ \"query\": \"mutation { updateBookDetails ( id: 1, title: \\\"My GraphQL with Springboot\\\", authorId: 1, publishedYear: 2025) { bookId title author { name } publishedYear } }\" }";
		
		Author author = new Author();
		author.setId(1L);
		author.setName("John Doe");
		
		Book book = new Book();
		book.setBookId(1L);
		book.setTitle("My GraphQL with Springboot");
		book.setPublishedYear(2025);
		book.setAuthor(author);
		
		when(bookService.updateBookDetails(any(Book.class), eq(1L), eq(1L))).thenReturn(book);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.updateBookDetails.bookId").value(book.getBookId()))
		.andExpect(jsonPath("$.data.updateBookDetails.title").value(book.getTitle()))
		.andExpect(jsonPath("$.data.updateBookDetails.publishedYear").value(book.getPublishedYear()))
		.andExpect(jsonPath("$.data.updateBookDetails.author.id").value(book.getAuthor().getId()))
		.andExpect(jsonPath("$.data.updateBookDetails.author.name").value(book.getAuthor().getName()));
		
		verify(bookService, times(1)).updateBookDetails(any(Book.class), eq(1L), eq(1L));
	}
}
