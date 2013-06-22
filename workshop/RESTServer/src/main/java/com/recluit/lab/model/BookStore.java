package com.recluit.lab.model;

import java.util.ArrayList;
import java.util.List;

public class BookStore
{
	private List<Book> allBooks;
	
	public BookStore() 
	{
		allBooks = new ArrayList<Book>();
		allBooks.add( new Book( "Sherlock Holmes", "Sir Arthur Conan Doyle" ) );
		allBooks.add( new Book( "Don Quijote de la Mancha", "Miguel de Cervantes Saavedra" ) );
	}
	
	public List<Book> getAllBooks() {
		return allBooks;
	}
	
	public void setAllBooks( List<Book> books ) {
		this.allBooks = books;
	}
}
