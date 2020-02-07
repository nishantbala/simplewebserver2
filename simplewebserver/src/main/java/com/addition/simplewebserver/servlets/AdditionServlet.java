package com.addition.simplewebserver.servlets;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.addition.simplewebserver.exception.AdditionException;
import com.addition.simplewebserver.service.AdditionService;
import com.addition.simplewebserver.service.AdditionServiceImpl;

@Path("/")
public class AdditionServlet {
	
	private AdditionService additionService = new AdditionServiceImpl();
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String processRequest(String payloadRequest) {
		String result;
		try {
				result = additionService.processRequest(payloadRequest);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				result = e.getMessage();
			} catch (NumberFormatException | AdditionException e) {
				result = e.getMessage();
			}
		return result;
	}
}