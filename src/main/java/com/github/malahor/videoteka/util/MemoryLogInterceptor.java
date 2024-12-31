package com.github.malahor.videoteka.util;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import lombok.extern.slf4j.Slf4j;

@MemoryLog
@Interceptor
@Priority(Interceptor.Priority.PLATFORM_AFTER)
@Slf4j
public class MemoryLogInterceptor {

  @AroundInvoke
  Object registerMemoryLog(InvocationContext context) throws Exception {
    var startTime = System.currentTimeMillis();
    var methodName = context.getMethod().getName();
    log.info("START {}, memory in use: {}", methodName, memory());
    var result = context.proceed();
    var endTime = System.currentTimeMillis();
    var duration = (endTime - startTime);
    log.info("FINISH {}, memory in use: {}, execution time: {}", methodName, memory(), duration);
    return result;
  }

  private long memory() {
    return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
  }
}
