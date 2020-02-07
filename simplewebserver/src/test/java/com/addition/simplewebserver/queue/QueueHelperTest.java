package com.addition.simplewebserver.queue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;

import com.addition.simplewebserver.exception.AdditionException;

public class QueueHelperTest {

	
    @Test
    public void testResetResult() {
    	QueueHelper.resetResult();
    	boolean result = QueueHelper.getResult().equals(BigInteger.ZERO);
    	assertTrue(result);
    }
    
    @Test
    public void testAddNumberToQueue() throws AdditionException {
    	QueueHelper.addNumberToQueue(BigInteger.valueOf(10000));
    	boolean result = QueueHelper.getSum().equals(BigInteger.valueOf(10000));
    	assertTrue(result);
    }
    
    @Test
    public void testCanAccept() throws AdditionException {
    	QueueHelper.addNumberToQueue(BigInteger.valueOf(10000));
    	boolean result = QueueHelper.canAccept();
    	assertTrue(result);
    }
    
    @Test
    public void testCanAccept1() throws AdditionException {
    	for(int i=0; i<=20;i++) {
    		QueueHelper.addNumberToQueue(BigInteger.valueOf(i));    		
    	}
    	boolean result = QueueHelper.canAccept();
    	QueueHelper.getSumAndForget();
    	assertFalse(result);
    }
}
