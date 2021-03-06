package com.addition.simplewebserver.queue;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.addition.simplewebserver.common.Constants;
import com.addition.simplewebserver.exception.AdditionException;

public class QueueHelper {  
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
	
	private static Queue<BigInteger> queue = new ConcurrentLinkedQueue<>(); 
	private static boolean canRespond = false;
	private static boolean canUpdate = true;
	private static BigInteger result = BigInteger.ZERO;
	
	private QueueHelper() {
		
	}
	
	public static void resetResult() {
    	result = BigInteger.ZERO;
	}
	
    public static void addNumberToQueue(BigInteger number) throws AdditionException {
    	if(canUpdate) {
    		canRespond = false;
        	if(queue.add(number)) {
        		logger.info("Successfully Added Number to Queue, {}", number);
        	}
    	} else {
    		try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new AdditionException(e.getMessage());
			}
    		addNumberToQueue(number);
    	}
    	
    }
    
    public static BigInteger getSum() {
    	BigInteger sum = BigInteger.ZERO;
    	if(!queue.isEmpty()) {
    		for (BigInteger item: queue) {
    			canUpdate = false;
        		sum = sum.add(item);
    		}
    	}
    	canUpdate = true;
    	canRespond = true;
    	return sum;
    }
    
    public static BigInteger getResult() {
    	return result;
    }
    
    public static BigInteger getSumAndForget() throws AdditionException {
    	for (BigInteger item: queue) {
    		canUpdate = false;
    		result = result.add(item);
		}
    	canUpdate = true;
    	deleteEntries();
        canRespond = true;
        return result;	
    }
    
    private static void deleteEntries() throws AdditionException {
    	if(canUpdate) {
    		queue.clear();
    		logger.info("Cleared all entries from queue");
    	} else {
    		try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new AdditionException(e.getMessage());
			}
    		deleteEntries();
    	}
    }
    
    public static boolean canRespond() {
    	return canRespond;
    }
    
    public static boolean canAccept() {
    	boolean canAccept = false;
    	if(queue.size() < Constants.MAX_REQUEST) {
    		canAccept = true;
    	} 
    	return canAccept;
    }
} 