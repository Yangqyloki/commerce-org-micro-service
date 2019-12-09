package sap.commerce.org.unitservice.handler;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.AUTHORIZATION;
import static sap.commerce.org.unitservice.constants.UnitServiceConstants.B2B_ADMIN_GROUP;
import static sap.commerce.org.unitservice.constants.UnitServiceConstants.BASE_SITE_ID;
import static sap.commerce.org.unitservice.constants.UnitServiceConstants.UNIT_ID;
import static sap.commerce.org.unitservice.constants.UnitServiceConstants.USER_ID;
import static sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors.INVALID_REQUEST;
import static sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors.INVALID_REQUEST_BASE_SITE_ID;
import static sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors.INVALID_REQUEST_REQUEST_BODY;
import static sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors.INVALID_REQUEST_REQUEST_HEADER;
import static sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors.INVALID_REQUEST_USER_ID;
import static sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors.MISSING_PARAMETER;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UnitClient;
import sap.commerce.org.unitservice.client.UserClient;
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.OccCustomerDTO;
import sap.commerce.org.unitservice.dto.UnitDTO;
import sap.commerce.org.unitservice.dto.AbstractDTO;
import sap.commerce.org.unitservice.utils.DTOConverter;
import sap.commerce.org.unitservice.exceptions.UnitServiceException;
import sap.commerce.org.unitservice.exceptions.errors.UnitServiceErrors;

@Component
public class UnitServiceHandler {

    @Autowired
    private UserClient userClient;

    @Autowired
    private UnitClient unitClient;

    public Mono<ServerResponse> getUnitsByUser(final ServerRequest request) {
        validateCreateUnitPath(request.pathVariables());
        validateRequestHeader(request.headers());
        return userClient.getCustomerById(request)
            .flatMap(occCustomerDTO -> unitClient.getUnitsByUser(occCustomerDTO.getUid()))
            .flatMap(userGroups -> EntityResponse.fromObject(userGroups).contentType(MediaType.APPLICATION_JSON)
                .status(HttpStatus.OK).build());

        //
        //
        // unitClient.getUnitsByUser(request.pathVariable(USER_ID)).flatMap(userGroups -> EntityResponse
        // .fromObject(userGroups).contentType(MediaType.APPLICATION_JSON).status(HttpStatus.OK).build());

    }

    public Mono<ServerResponse> createUnit(final ServerRequest request) {
        validateCreateUnitPath(request.pathVariables());
        // validateRequestBody(request.bodyToMono(UnitDTO.class).map(unit -> validateRequestBody(unit)));
        validateRequestHeader(request.headers());
        return request.bodyToMono(UnitDTO.class).flatMap(unit -> {
            validateRequestBody(unit);
            return userClient.getUserGroups(request).flatMap(userGroupList -> {
                if (userGroupList.getUserGroups().stream().filter(group -> B2B_ADMIN_GROUP.equals(group.getUid()))
                    .findAny().isPresent()) {
                    return unitClient.creatUnit(request.pathVariable(USER_ID), unit);
                }
                return Mono.error(new UnitServiceException(HttpStatus.BAD_REQUEST, INVALID_REQUEST));
            });
        }).flatMap(result -> EntityResponse.fromObject(result).contentType(MediaType.APPLICATION_JSON)
            .status(HttpStatus.OK).build());
    }

    public Mono<ServerResponse> createCustomerForUnit(final ServerRequest request) {
        validateCreateUnitPath(request.pathVariables());
        // validateRequestBody(request.bodyToMono(CustomerDTO.class).map(unit -> validateRequestBody(unit)));
        validateRequestHeader(request.headers());
        return userClient.getUserGroups(request).flatMap(userGroupList -> {
            // System.out.println("YQY userGroupList: " + userGroupList);
            if (userGroupList.getUserGroups().stream().filter(group -> B2B_ADMIN_GROUP.equals(group.getUid())).findAny()
                .isPresent()) {
                // System.out.println("If Seg!!!!");
                return request.bodyToMono(CustomerDTO.class).flatMap(customerInRequest -> {
                    validateRequestBody(customerInRequest);
                    OccCustomerDTO occCustomer = DTOConverter.convertCustomer(customerInRequest);
                    // System.out.println("occCustomer: " + occCustomer);
                    return userClient.createCustomer(request, occCustomer).flatMap((occCustomerInResopnse) -> {
                        // System.out.println("Set User Group!!!!!!");
                        userClient.setCustomerToUserGroup(request, customerInRequest);
                        return unitClient.createCustomerForUnit(request.pathVariable(UNIT_ID), customerInRequest);
                    });
                });

            }
            return Mono.error(new UnitServiceException(HttpStatus.BAD_REQUEST, INVALID_REQUEST));

        }).flatMap(unit -> EntityResponse.fromObject(unit).contentType(MediaType.APPLICATION_JSON).status(HttpStatus.OK)
            .build());
    }

    private void validateCreateUnitPath(final Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            throw new UnitServiceException(HttpStatus.BAD_REQUEST, MISSING_PARAMETER);
        }
        validateRequestPath(variables.get(BASE_SITE_ID), INVALID_REQUEST_BASE_SITE_ID);
        validateRequestPath(variables.get(USER_ID), INVALID_REQUEST_USER_ID);
    }

    private void validateRequestPath(final String path, final UnitServiceErrors error) {
        if (path == null || path.isBlank()) {
            throw new UnitServiceException(HttpStatus.BAD_REQUEST, error);
        }
    }

    private <T extends AbstractDTO> void validateRequestBody(final T body) {
        if (body == null || body.checkIfDTOEmpty()) {
            throw new UnitServiceException(HttpStatus.BAD_REQUEST, INVALID_REQUEST_REQUEST_BODY);
        }
    }

    private void validateRequestHeader(final ServerRequest.Headers headers) {
        if (CollectionUtils.isEmpty(headers.header(AUTHORIZATION)) || headers.header(AUTHORIZATION).get(0) == null) {
            throw new UnitServiceException(HttpStatus.BAD_REQUEST, INVALID_REQUEST_REQUEST_HEADER);
        }
    }

}
