package co.ke.proaktiv.io.configurations;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(CustomAsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
		log.info("***** Exception message: "+arg0.getMessage());
		log.info("***** Method name: "+arg1.getName());
		for(Object param : arg2) {
			log.info("***** Parameter value: "+param);
		}

	}

}
