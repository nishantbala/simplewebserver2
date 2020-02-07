package com.addition.simplewebserver.queue;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.addition.simplewebserver.common.Constants;
import com.addition.simplewebserver.exception.AdditionException;

public class QueueHelper {  
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
	
	private static Queue<BigInteger> queue = new LinkedList<>(); 
	private static boolean canRespond = false;
	private static boolean canDelete = true;
	private static BigInteger result = BigInteger.ZERO;
	
	private QueueHelper() {
		
	}
	
	public static void resetResult() {
    	result = BigInteger.ZERO;
	}
	
    public static void addNumberToQueue(BigInteger number) {
    	canRespond = false;
    	if(queue.add(number)) {
    		logger.info("Successfully Added Number to Queue, {}", number);
    	}
    }
    
    public static BigInteger getSum() {
    	BigInteger sum = BigInteger.ZERO;
    	if(!queue.isEmpty()) {
    		for (BigInteger item: queue) {
        		sum = sum.add(item);
        		canDelete = false;
    		}
    	}
    	canDelete = true;
    	canRespond = true;
    	return sum;
    }
    
    public static BigInteger getResult() {
    	return result;
    }
    
    public static BigInteger getSumAndForget() throws AdditionException {
    	for (BigInteger item: queue) {
    		result = result.add(item);
		}
    	deleteEntries();
        canRespond = true;
        return result;	
    }
    
    private static void deleteEntries() throws AdditionException {
    	if(canDelete) {
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