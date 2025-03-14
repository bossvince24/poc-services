package com.example.book.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.book.entity.Author;
import com.example.book.serviceImpl.AuthorServiceImpl;

@Controller
public class AuthorResolver {
	
	@Autowired
	private AuthorServiceImpl serviceImpl;
	
	@MutationMapping
	public Author addAuthor(@Argument String name) {
		return serviceImpl.addAuthor(name);
	}
	
	public Author getAuthorById(Long authorId) {
		return serviceImpl.getAuthorById(authorId);
	}
	
	@QueryMapping
	public List<Author> fetchAllAuthors(@Argument int page, @Argument int size) {
		return serviceImpl.fetchAllAuthors(page, size);
	}
}
