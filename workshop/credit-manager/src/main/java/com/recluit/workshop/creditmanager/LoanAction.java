package com.recluit.lab.action;

import com.opensymphony.xwork2.ActionSupport;

public class LoanAction extends ActionSupport
{
	private static final long serialVersionUID = -1603945542077827960L;
	
	private String rfc;
	private int operation;
	
	public String execute() throws Exception
	{
		String opt = "";
		operation = getOperation();
		
		switch( operation )
		{
			case 1:
				opt = "search";
				break;
			case 2:
				opt = "close";
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