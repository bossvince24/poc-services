package com.example.book.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByAuthorIdAndPublishedYear(Long authorId, Integer publishedYear, PageRequest of);

	List<Book> findByAuthorId(Long authorId, PageRequest of);

	List<Book> findByPublishedYear(Integer publishedYear, PageRequest of);
	
	


}
