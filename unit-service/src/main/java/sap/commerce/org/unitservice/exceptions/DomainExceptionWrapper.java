package sap.commerce.org.unitservice.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import lombok.extern.slf4j.Slf4j;
import sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors;

@Slf4j
@Component
public class DomainExceptionWrapper extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(final ServerRequest request, final boolean includeStackTrace) {
        final var exception = getError(request);
        final var errorAttributes = super.getErrorAttributes(request, includeStackTrace);
        if (exception instanceof UnitServiceException) {
            final UnitServiceException use = (UnitServiceException) exception;
            final UnitServiceErrors error = use.getError();
            System.out.println("Wapper and error is: " + error);
            final Map<String, String> reason = new HashMap<>();
            reason.put("code", error.getCode());
            reason.put("message", error.getMessage());
            errorAttributes.put("reason", reason);
            return errorAttributes;
        }
        return errorAttributes;
    }

}
