package com.addition.simplewebserver.service;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.addition.simplewebserver.common.Constants;
import com.addition.simplewebserver.exception.AdditionException;
import com.addition.simplewebserver.queue.QueueHelper;

public class AdditionServiceImpl implements AdditionService{

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
	
	@Override
	public String processRequest(String payloadRequest)
			throws InterruptedException, AdditionException {
		QueueHelper.resetResult();
		validateRequest(payloadRequest);
		boolean isEndOfRequest = isEndOfRequest(payloadRequest);
		BigInteger result;
		if(isEndOfRequest) {
			result = QueueHelper.getSumAndForget();
			logger.info("isEndOfRequest");
		} else {
			QueueHelper.addNumberToQueue(new BigInteger(payloadRequest));
			while(!QueueHelper.canRespond()){
				TimeUnit.SECONDS.sleep(1);
			}
			result = QueueHelper.getResult();
		}
		return String.valueOf(result);
	}
	
	@Override
	public boolean isEndOfRequest(String payloadRequest) {
		return payloadRequest.equals(Constants.END_STRING);
	}
	
	@Override
	public void validateRequest(String payloadRequest) throws AdditionException {
		if(!isEndOfRequest(payloadRequest)) {
			checkCurrentRequestCount();
			try {
				new BigInteger(payloadRequest);
				checkCurrentSum(payloadRequest);
			} catch (NumberFormatException e){
				throw new AdditionException(Constants.ERROR_MSG_INVALID_INPUT+payloadRequest);
			} catch (AdditionException e){
				throw new AdditionException(e.getMessage());
			}
			
		}
	}
	public void checkCurrentSum(String payloadRequest) throws AdditionException {
		BigInteger currentSum = QueueHelper.getSum();
		BigInteger currentValue = currentSum.add(new BigInteger(payloadRequest));
		if(currentValue.compareTo(new BigInteger(Constants.MAX_VALUE)) > 0) {
			throw new AdditionException(Constants.ERROR_MSG_MAX_VALUE_EXCEEDED);
		}
	}
	
	public void checkCurrentRequestCount() throws AdditionException {
		if(!QueueHelper.canAccept()) {
    		logger.info(Constants.ERROR_MSG_MAX_REQUEST_EXCEEDED);
			throw new AdditionException(Constants.ERROR_MSG_MAX_REQUEST_EXCEEDED);
		}
	}
}
