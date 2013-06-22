package com.recluit.lab.restservices;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import com.recluit.lab.restservices.ClientSockect;



@Path("/loan")
public class LoanService
{
	@GET
	@Path("/{rfc}")
	@Consumes(MediaType.TEXT_PLAIN)
	public String getLoans( @PathParam("rfc") String rfc )
	{
		ArrayList<String> loans;
		ClientSockect sock = new ClientSockect();
		//loans = sock.ClientSocket(rfc, "s");
		loans = sock.ClientSocket(rfc);
		String loan = "";
		
		for( String line : loans )
		{
			loan += line + "$";
		}
		return loan;
	}
}
