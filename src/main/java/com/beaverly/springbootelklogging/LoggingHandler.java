package com.beaverly.springbootelklogging;

public interface LoggingHandler<T> {
    void processLogging(T entity, Object body);
}
