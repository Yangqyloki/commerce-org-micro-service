package sap.commerce.org.unitservice.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

import sap.commerce.cloud.hbcloudcommons.error.Errors;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UnitServiceErrors implements Errors {
    INVALID_REQUEST("10000", "The request is invalid."), MISSING_PARAMETER("10001",
        "Required path parameter is missing."), INVALID_REQUEST_BASE_SITE_ID("10002",
            "baseSiteId is missing"), INVALID_REQUEST_UNIT_ID("10003", "unitId is missing"), INVALID_REQUEST_USER_ID(
                "10004", "userId is missing"), MISSING_PARAMETER_TENANT_ID("10005",
                    "TenantId parameter is missing."), INVALID_REQUEST_REQUEST_BODY("10006",
                        "Request body is missing"), INVALID_REQUEST_REQUEST_HEADER("10007",
                            "Request header is missing"), INVALID_USER_NOT_ADMIN("10008", "Status is missing");

    private final String code;
    private final String message;

    UnitServiceErrors(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
