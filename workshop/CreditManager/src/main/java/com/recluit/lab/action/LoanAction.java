package com.recluit.lab.action;

import com.opensymphony.xwork2.ActionSupport;
import com.recluit.lab.restclient.LoanRestClient;

public class LoanAction extends ActionSupport
{
	private static final long serialVersionUID = -1603945542077827960L;
	
	private String rfc;
	private int operation;
	private String fname;
	private String country;
	private String loanQuery;
	private String[][] loans;
	private int id_loan;
	
	public int getId_loan() {
		return id_loan;
	}

	public void setId_loan(int id_loan) {
		this.id_loan = id_loan;
	}

	public String[][] getLoans() {
		return loans;
	}

	public void setLoans(String[][] loans) {
		this.loans = loans;
	}

	public String getLoanQuery() {
		return loanQuery;
	}

	public void setLoanQuery(String loanQuery) {
		this.loanQuery = loanQuery;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	private double loan_amount;
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public double getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(double loan_amount) {
		this.loan_amount = loan_amount;
	}
	
	public String execute() throws Exception
	{
		String opt = "";
		operation = getOperation();	
		String line = "";
		LoanRestClient loanRestClient = new LoanRestClient();
		
		switch( operation )
		{
			case 1:
				line = loanRestClient.getLoans( "s" + loanQuery );
				rfc = loanQuery;
				
				if( line.contains("Not Found") == false )
				{
					loans = new String [line.split("\\$").length][8];
					for(int i = 0 ; i < line.split("\\$").length ; i++)
					{
						loans[i] = line.split("\\$")[i].split("\\|");
					}
					opt = "search";
				} else {
					opt = "not_found";
				}
				break;
			case 2:
				line = loanRestClient.getLoans( "c" + loanQuery );
				rfc = loanQuery;
				if( id_loan == 0 )
				{
					if( line.contains("Not Found") == false )
					{
						loans = new String [line.split("\\$").length][8];
						for(int i = 0 ; i < line.split("\\$").length ; i++)
						{
							loans[i] = line.split("\\$")[i].split("\\|");
						}
						opt = "close";
					} else {
						opt = "not_found";
					}
				} else
				{
					opt = "closed";
				}
				break;
			case 3:
				opt = "add";
				break;
			case 4:
				opt = "payment";
				break;
		}
		return opt;
	}
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
}