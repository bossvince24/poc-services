package com.example.book.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.book.entity.Author;
import com.example.book.exception.AuthorNotFoundException;
import com.example.book.repository.AuthorRepository;
import com.example.book.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	@Autowired
	private AuthorRepository repository;

	@Override
	public List<Author> fetchAllAuthors(int page, int size) {
		// TODO Auto-generated method stub
		return repository.findAll(PageRequest.of(page, size)).getContent();
	}

	@Override
	public Author addAuthor(String name) {
		// TODO Auto-generated method stub
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Author name cannot be empty");
		}
		Author author = new Author();
		author.setName(name);
		return repository.save(author);
	}

	@Override
	public Author getAuthorById(Long id) throws AuthorNotFoundException {
		// TODO Auto-generated method stub
		return repository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author with ID " + id + " not found."));
	}
	

}
