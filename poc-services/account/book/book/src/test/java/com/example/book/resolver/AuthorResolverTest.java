package com.example.book.resolver;


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
import com.example.book.serviceImpl.AuthorServiceImpl;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorResolverTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private AuthorServiceImpl service;
	
	@DisplayName("Add Author Test")
	@Test
	public void testAddAuthor() throws Exception {
		
		String query = "{ \"query\": \"mutation { addAuthor (name: \\\"John Doe\\\" ){ id name } }\" }";
		
		
		Author mockAuthor = new Author();
		mockAuthor.setId(1L);
		mockAuthor.setName("John Doe");
		
		when(service.addAuthor("John Doe")).thenReturn(mockAuthor);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.addAuthor.id").value(mockAuthor.getId()))
		.andExpect(jsonPath("$.data.addAuthor.name").value(mockAuthor.getName()));
		
		verify(service, times(1)).addAuthor(mockAuthor.getName());
	}
	
	@DisplayName("Fetch All Authors Test")
	@Test
	public void testFetchAllAuthors() throws Exception {
		
		String query = "{ \"query\": \"query { fetchAllAuthors(page: 1, size: 1) { id name books { bookId title publishedYear } } }\" }";
		
		Author author = new Author();
		author.setId(1L);
		author.setName("John Doe");
		
		List<Author> authors = Arrays.asList(author);
		
		when(service.fetchAllAuthors(1, 1)).thenReturn(authors);
		
		mockMvc.perform(post("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.content(query))
		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.data.fetchAllAuthors").isArray())
		.andExpect(jsonPath("$.data.fetchAllAuthors[0].id").value(author.getId()))
		.andExpect(jsonPath("$.data.fetchAllAuthors[0].name").value(author.getName()));
		
		verify(service, times(1)).fetchAllAuthors(1, 1);
		
	}
}
