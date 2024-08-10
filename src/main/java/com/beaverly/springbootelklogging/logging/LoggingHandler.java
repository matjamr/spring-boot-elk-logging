package com.beaverly.springbootelklogging.logging;

public interface LoggingHandler<T> {
    void processLogging(T entity, Object body);
}
