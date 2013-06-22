package com.recluit.lab.restservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.recluit.lab.model.Book;
import com.recluit.lab.model.BookStore;

@Path("/book")
public class BookService
{
	@GET
	@Path("/{title}")
	@Consumes(MediaType.TEXT_PLAIN)
	public String getBook( @PathParam("title") String title )
	{
		System.out.println("Book to query: " + title);
		BookStore bookstore = new BookStore();
		
		for( Book book : bookstore.getAllBooks() )
		{
			if( title.equals( book.getTitle() ) ) {
				System.out.println("Book found : " + book);
				String result = book.getTitle() + ", " +
						book.getAuthor();
				return result;
			}
		}
		return null;
	}
}
