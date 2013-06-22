package com.recluit.lab.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class HelloClient
{
	public String getMessage()
	{
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/RESTServer/rest/hello");
		ClientResponse response = webResource.accept("text/plain").get(ClientResponse.class);
		
		if( response.getStatus() != 200 )
		{
			throw new RuntimeException( "Failed!: " + response.getStatus() );
		}
		
		String output = response.getEntity(String.class);
		return output;
	}
}
