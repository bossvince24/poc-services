package com.example.book.service;

import java.util.List;

import com.example.book.entity.Author;
import com.example.book.exception.AuthorNotFoundException;

public interface AuthorService {

	List<Author> fetchAllAuthors(int page, int size);
	
	Author addAuthor(String name);
	
	Author getAuthorById(Long id) throws AuthorNotFoundException;
	
}
