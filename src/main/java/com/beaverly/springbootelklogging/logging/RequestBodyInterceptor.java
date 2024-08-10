package com.beaverly.springbootelklogging.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter implements LoggingHandler<HttpServletRequest> {

    private final HttpServletRequest httpServletRequest;
    private static final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        this.processLogging(httpServletRequest, body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    @SneakyThrows
    public void processLogging(HttpServletRequest request, Object body) {
        StringBuilder reqMessage = new StringBuilder();
        Map<String,String> parameters = getParameters(request);
        Map<String,String> headers = getHeaders(request);

        reqMessage.append("REQUEST ");
        reqMessage.append("method = [").append(request.getMethod()).append("]");
        reqMessage.append(" path = [").append(request.getRequestURI()).append("] ");
        if(!parameters.isEmpty()) {
            reqMessage.append(" parameters = [").append(parameters).append("] ");
        }

        if(!headers.isEmpty()) {
            reqMessage.append(" headers = [").append(headers).append("]");
        }

        if(!Objects.isNull(body)) {
            reqMessage.append("\nbody = [").append(objectWriter.writeValueAsString(body)).append("]");
        }

        log.info(reqMessage.toString());
    }

    private Map<String,String> getParameters(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            parameters.put(paramName,paramValue);
        }
        return parameters;
    }

    private Map<String,String> getHeaders(HttpServletRequest request) {
        Map<String,String> headers = new HashMap<>();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while(headerEnumeration.hasMoreElements()) {
            String headerName = headerEnumeration.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers;
    }
}
