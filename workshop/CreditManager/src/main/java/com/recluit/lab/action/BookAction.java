package com.recluit.lab.action;

import java.util.Scanner;

import com.opensymphony.xwork2.ActionSupport;
//import com.recluit.lab.restclient.HelloClient;
import com.recluit.lab.restclient.BookRestClient;


public class BookAction extends ActionSupport
{
	private static final long serialVersionUID = -3561538112932319256L;
	
	private String bookQuery;
	//private BookStore bookStore;
	private String bookResult;
	private String bookResultTitle;
	private String bookResultAuthor;

	public String execute() throws Exception
	{
		//bookStore = new BookStore();
		BookRestClient bookRestClient = new BookRestClient();
		bookResult = bookRestClient.finBookByTitle(bookQuery);
		
		if(bookResult != null)
		{
			Scanner scanner = new Scanner( bookResult );
			scanner.useDelimiter(", ");
			bookResultTitle = scanner.next();
			bookResultAuthor = scanner.next();
			scanner.close();
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String getBookQuery() {
		return bookQuery;
	}

	public void setBookQuery(String bookQuery) {
		this.bookQuery = bookQuery;
	}

	public String getBookResultTitle() {
		return bookResultTitle;
	}

	public void setBookResultTitle(String bookResultTitle) {
		this.bookResultTitle = bookResultTitle;
	}

	public String getBookResultAuthor() {
		return bookResultAuthor;
	}

	public void setBookResultAuthor(String bookResultAuthor) {
		this.bookResultAuthor = bookResultAuthor;
	}
}