package org.alx.article._41_thread_local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import java.util.UUID;

public class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);
    private static final ThreadLocal<String> requestId = new ThreadLocal<>();

    public void processRequest() {
        // Set request ID in the MDC context
        requestId.set(generateRequestId());
        MDC.put("requestId", requestId.get());

        // Perform some processing
        logger.info("Processing request...");

        // Clear the MDC context after processing
        requestId.remove();
        MDC.clear();
    }

    private String generateRequestId() {
        // Generate a unique request ID
        return UUID.randomUUID().toString();
    }
}