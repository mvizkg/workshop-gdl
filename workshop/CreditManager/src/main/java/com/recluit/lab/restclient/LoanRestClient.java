package com.recluit.lab.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LoanRestClient
{
	public String getLoans( String rfc )
	{
		Client client = Client.create();
		String url = "http://localhost:8080/RESTServer/rest/loan/" + rfc;
		WebResource resource = client.resource(url);
		ClientResponse response = resource.accept("text/plain").get(ClientResponse.class);
		
		if( response.getStatus() == 200 ) {
			return response.getEntity( String.class );
		} else {
			return null;
		}
	}
	
	public String cancelLoans( String rfc, int id_loan ){
		Client client = Client.create();
		String url = "http://localhost:8080/RESTServer/rest/loan/" + rfc + id_loan;
		WebResource resource = client.resource(url);
		ClientResponse response = resource.accept("text/plain").get(ClientResponse.class);
		
		if( response.getStatus() == 200 ) {
			return response.getEntity( String.class );
		} else {
			return null;
		}
	}
}