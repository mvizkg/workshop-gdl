package com.recluit.lab.restservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class MessageService
{
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello from RESTServer!!! :)";
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXmlHello() {
		return "<?xml version=\"1.0\"?>" +
				"<hello>Hello from RESTServer!!!</hello>";
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return new StringBuilder("")
				.append("<html>")
				.append("\t<head><title>Hello =)</title></head>")
				.append("\t<body><h1>Hello from RESTServer!!!</h1></body>")
				.append("</html>").toString();
	}
}
