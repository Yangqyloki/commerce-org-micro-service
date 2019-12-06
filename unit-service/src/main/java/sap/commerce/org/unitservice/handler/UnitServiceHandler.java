package sap.commerce.org.unitservice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sap.commerce.cloud.hbcloudcommons.error.Errors;
import sap.commerce.cloud.hbcloudcommons.error.GlobalWebException;
import sap.commerce.org.unitservice.client.UnitClient;
import sap.commerce.org.unitservice.client.UserClient;
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.OccCustomerDTO;
import sap.commerce.org.unitservice.dto.UnitDTO;
import sap.commerce.org.unitservice.dto.utils.DTOConverter;

import java.util.List;
import java.util.Map;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.*;
import static sap.commerce.org.unitservice.errors.UnitServiceErrors.*;

@Component
public class UnitServiceHandler {

    @Autowired
    private UserClient userClient;

    @Autowired
    private UnitClient unitClient;

    public Mono<ServerResponse> getUnitsByUser(final ServerRequest request){
//        validateCreateUnitPath(request.pathVariables());
//        validateRequestBody(request.bodyToMono(UnitDTO.class).map(unit->validateRequestBody(unit)));
//        validateRequestHeader(request.headers());
        return unitClient.getUnitsByUser(request.pathVariable(USER_ID)).
                flatMap(userGroups -> EntityResponse.fromObject(userGroups).contentType(MediaType.APPLICATION_JSON).
                        status(HttpStatus.OK).build());

    }

    public Mono<ServerResponse> createUnit(final ServerRequest request){
//        validateCreateUnitPath(request.pathVariables());
//        validateRequestBody(request.bodyToMono(UnitDTO.class).map(unit->validateRequestBody(unit)));
//        validateRequestHeader(request.headers());
        return  request.bodyToMono(UnitDTO.class).
                flatMap(unit -> unitClient.creatUnit(request.pathVariable(USER_ID), unit)).
                flatMap(result -> EntityResponse.fromObject(result).contentType(MediaType.APPLICATION_JSON).
                        status(HttpStatus.OK).build());
    }

    public Mono<ServerResponse> createCustomerForUnit(final ServerRequest request){
//        validateCreateUnitPath(request.pathVariables());
//        validateRequestBody(request.bodyToMono(CustomerDTO.class).map(unit->validateRequestBody(unit)));
//        validateRequestHeader(request.headers());
        return userClient.getUserGroups(request).flatMap(userGroupList -> {
            System.out.println("YQY userGroupList: " + userGroupList);
            if(userGroupList.getUserGroups().stream().filter(group-> B2B_ADMIN_GROUP.equals(group.getUid())).findAny().isPresent())
            {
                System.out.println("If Seg!!!!");
                return request.bodyToMono(CustomerDTO.class).flatMap(customerInRequest->{
                    OccCustomerDTO occCustomer = DTOConverter.convertCustomer(customerInRequest);
                    System.out.println("occCustomer: " + occCustomer);
                    return userClient.createCustomer(request,occCustomer).flatMap((occCustomerInResopnse)->{
                        System.out.println("Set User Group!!!!!!");
                        userClient.setCustomerToUserGroup(request,customerInRequest);
                        return unitClient.createCustomerForUnit(request.pathVariable(UNIT_ID),customerInRequest);
                    });
                });

            }
            return Mono.error(new GlobalWebException(HttpStatus.BAD_REQUEST, INVALID_REQUEST, List.of(INVALID_USER_NOT_ADMIN)));

        }).flatMap(unit->EntityResponse.fromObject(unit).contentType(MediaType.APPLICATION_JSON).
                status(HttpStatus.OK).build());
    }

    private void validateCreateUnitPath(Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            throw new GlobalWebException(HttpStatus.BAD_REQUEST, INVALID_REQUEST, List.of(MISSING_PARAMETER));
        }
        validateRequestPath(variables.get(BASE_SITE_ID), INVALID_REQUEST_BASE_SITE_ID);
        validateRequestPath(variables.get(USER_ID), INVALID_REQUEST_USER_ID);
    }

    private void validateCreateCustomerPath(Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            throw new GlobalWebException(HttpStatus.BAD_REQUEST, INVALID_REQUEST, List.of(MISSING_PARAMETER));
        }
        validateRequestPath(variables.get(BASE_SITE_ID), INVALID_REQUEST_BASE_SITE_ID);
        validateRequestPath(variables.get(USER_ID), INVALID_REQUEST_USER_ID);
        validateRequestPath(variables.get(UNIT_ID), INVALID_REQUEST_UNIT_ID);
    }

    private void validateRequestPath(String path, Errors error) {
        if (path == null || path.isBlank()) {
            throw new GlobalWebException(HttpStatus.BAD_REQUEST, MISSING_PARAMETER,
                    List.of(error));
        }
    }

    private <T> T validateRequestBody(T body) {
//        System.out.println("validateRequestBody: " + body);
//        request.bodyToMono(UnitRequestDTO.class).doOnError();
//        request.bodyToMono(UnitRequestDTO.class).map(unit->{
//            System.out.println("validateRequestBody unit: " + unit);
//            if(unit == null){
//                        throw new GlobalWebException(HttpStatus.BAD_REQUEST, INVALID_REQUEST,
//                                List.of(INVALID_REQUEST_REQUEST_BODY));
//                    }
//                    return unit;
//                }
//        );
        if (body == null) {
            throw new GlobalWebException(HttpStatus.BAD_REQUEST, INVALID_REQUEST,
                    List.of(INVALID_REQUEST_REQUEST_BODY));
        }
        return body;
    }

    private void validateRequestHeader(final ServerRequest.Headers headers) {
        if (CollectionUtils.isEmpty(headers.header(AUTHORIZATION)) || headers.header(AUTHORIZATION).get(0) == null) {
            throw new GlobalWebException(HttpStatus.BAD_REQUEST, INVALID_REQUEST,
                    List.of(INVALID_REQUEST_REQUEST_HEADER));
        }

    }

}
