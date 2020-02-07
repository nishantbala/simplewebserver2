package com.addition.simplewebserver.service;

import com.addition.simplewebserver.exception.AdditionException;

public interface AdditionService {
	String processRequest(String payloadRequest)
			throws InterruptedException, AdditionException;

	boolean isEndOfRequest(String string);

	void validateRequest(String string)  throws AdditionException;	
}
