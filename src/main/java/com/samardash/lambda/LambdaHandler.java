package com.samardash.lambda;

import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.samardash.Application;
import org.slf4j.MDC;

public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    public static final String TRACE_ID= "traceId";
    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
        } catch (ContainerInitializationException ex) {
            throw new RuntimeException("Unable to load spring boot application", ex);
        }
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest input, Context context) {
        // Set AWS request ID in MDC for tracing
        MDC.put(TRACE_ID, context.getAwsRequestId());
        return handler.proxy(input, context);
    }
}
