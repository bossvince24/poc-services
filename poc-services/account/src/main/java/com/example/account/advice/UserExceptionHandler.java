package com.example.account.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import com.example.account.exception.UserNotFoundException;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserExceptionHandler extends DataFetcherExceptionResolverAdapter {

	private final HttpServletRequest request;

	public UserExceptionHandler(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	@Override
	protected GraphQLError resolveToSingleError(Throwable  ex, DataFetchingEnvironment env) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		Map<String, Object> errorMap = new HashMap<>();
		
		if (ex instanceof MethodArgumentNotValidException validException) {
			status = HttpStatus.BAD_REQUEST;
			validException.getBindingResult().getFieldErrors().forEach(
					error -> errorMap.put(error.getField(), error.getDefaultMessage()));
		}
		else if (ex instanceof ResponseStatusException responseStatusException) {
			status = (HttpStatus) responseStatusException.getStatusCode();
			errorMap.put("errorMessage", responseStatusException.getReason());
		}
		else if (ex instanceof HttpMessageNotReadableException) {
			status = HttpStatus.BAD_REQUEST;
			errorMap.put("errorMessage", ex.getMessage());
		}
		else if (ex instanceof UserNotFoundException) {
			status = HttpStatus.NOT_FOUND;
			errorMap.put("errorMessage", ex.getMessage());
		}
		else {
			errorMap.put("errorMessage", ex.getMessage());
		}
		
		request.setAttribute("httpStatus", status.value());
		
		return GraphQLError.newError()
				.message("GraphQL Error")
				.path(env.getExecutionStepInfo().getPath())
				.extensions(Map.of("errorDetails",errorMap, "httpStatus",status.value()))
				.build();
		
	}
	
	
}
