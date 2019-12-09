package sap.commerce.org.unitservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors;

public class UnitServiceException extends ResponseStatusException {

    private final UnitServiceErrors error;

    public UnitServiceException(final HttpStatus status, final UnitServiceErrors error) {
        super(status);
        this.error = error;
    }

    public UnitServiceErrors getError() {
        return this.error;
    }

}
