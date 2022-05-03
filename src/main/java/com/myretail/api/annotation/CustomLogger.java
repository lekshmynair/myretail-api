package com.myretail.api.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CustomLogger {
    Logger log = LoggerFactory.getLogger(CustomLogger.class.getName());

    @Around("@annotation(MyRetailLoggable)")
    public Object logExecutionTime(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        ObjectMapper mapper = new ObjectMapper();
        long execTime = System.currentTimeMillis() - start;
        try {
            log.info("Request : {}, Method: {}, Result: {}, Execution Time: {} millis", Arrays.toString(jp.getArgs()), jp.getSignature(),
                    mapper.writeValueAsString(result), execTime);
        } catch (JsonProcessingException e) {
            log.error("json parsing failed " + e.getMessage());
        }
        return result;
    }
}
