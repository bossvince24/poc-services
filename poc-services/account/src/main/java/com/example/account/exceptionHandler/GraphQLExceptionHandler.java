//package com.example.account.exceptionHandler;
//
//
//import java.util.Map;
//
//import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//
//
//
//
//import graphql.GraphQLError;
//import graphql.schema.DataFetchingEnvironment;
//
//@Component
//public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
//	
//	@Override
//	protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//		
//		if (ex instanceof ResponseStatusException responseException) {
//			status = (HttpStatus) responseException.getStatusCode();
//		}
//		return GraphQLError.newError()
//				.message(ex.getMessage())
//				.path(env.getExecutionStepInfo().getPath())
//				.extensions(Map.of("httpStatus", status.value()))
//				.build();
//	}
//
//}
