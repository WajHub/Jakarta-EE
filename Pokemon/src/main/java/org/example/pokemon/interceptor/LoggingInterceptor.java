package org.example.pokemon.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.logging.Logger;

@LogAction
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LoggingInterceptor implements Serializable {

    @Inject
    private HttpServletRequest request;

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        String user = "SYSTEM";

        try {
            if (request.getUserPrincipal() != null) {
                user = request.getRemoteUser();
            }
        } catch (Exception e) {
        }

        if (user == null) {
            user = "ANONYMOUS";
        }

        String methodName = context.getMethod().getName();
        String resourceId = "-";
        Object[] parameters = context.getParameters();

        if (parameters.length > 0 && parameters[0] != null) {
            resourceId = parameters[0].toString();
        }

        LOGGER.info(String.format("LOG -> User: %s | Operation: %s | Resource: %s", user, methodName, resourceId));
        System.out.println("LOG -> User: " + user + " | Operation: " + methodName + " | Resource: " + resourceId);
        return context.proceed();
    }
}