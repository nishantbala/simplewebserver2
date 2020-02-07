package com.addition.simplewebserver.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.addition.simplewebserver.exception.AdditionException;

public class AdditionServiceImplTest {
	
	AdditionService additionServiceImpl = new AdditionServiceImpl();
	
    @Test
    public void testIsEndOfRequest() {
    	assertTrue(this.additionServiceImpl.isEndOfRequest("end"));
    }
    
    @Test
    public void testIsEndOfRequest1() {
    	assertFalse(this.additionServiceImpl.isEndOfRequest("1"));
    }
    
    @Test
    public void testValidateRequest() {
    	boolean result = true;
    	try {
    		this.additionServiceImpl.validateRequest("end");
    	}  catch(AdditionException e) {
    		result = false;
    	}
    	assertTrue(result);
    }
    
    @Test
    public void testValidateRequest1() {
    	String actual = null;
    	try {
    		this.additionServiceImpl.validateRequest("3.2");
    	} catch(Exception e) {
    		actual = e.getMessage();
    	}
    	assertEquals("Invalid input: 3.2", actual);
    }
}
