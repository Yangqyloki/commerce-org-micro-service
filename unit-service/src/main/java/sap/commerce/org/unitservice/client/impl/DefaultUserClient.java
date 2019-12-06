package sap.commerce.org.unitservice.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UserClient;
import sap.commerce.org.unitservice.dao.UnitDao;
import sap.commerce.org.unitservice.dto.*;
import sap.commerce.org.unitservice.dto.utils.DTOConverter;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.*;

@Component
public class DefaultUserClient implements UserClient {


    @Value("${user.service.usergroups.path}")
    private String userGroupsPath;

    @Value("${user.service.user.path}")
    private String userPath;

    @Value("${user.service.usergroup.set.path}")
    private String setUserGroupPath;


    @Autowired
    private WebClient userServiceWebClient;

    @Autowired
    private UnitDao unitDao;

    @Override
    public Mono<UserGroupListDTO> getUserGroups(ServerRequest request) {
        return  userServiceWebClient.get().uri(uriBuilder -> uriBuilder.path(userGroupsPath).build(request.pathVariable(BASE_SITE_ID), request.pathVariable(USER_ID)))
                .header(AUTHORIZATION,request.headers().header(AUTHORIZATION).get(0))
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UserGroupListDTO.class);
    }

    @Override
    public Mono<OccCustomerDTO> createCustomer(ServerRequest request, OccCustomerDTO occCustomer) {
        return userServiceWebClient.post().uri(uriBuilder -> uriBuilder.path(userPath)
                    .build(request.pathVariable(BASE_SITE_ID)))
                    .header(AUTHORIZATION,request.headers().header(AUTHORIZATION).get(0))
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(occCustomer))
                    .retrieve().bodyToMono(OccCustomerDTO.class);
    }


    @Override
    public void setCustomerToUserGroup(ServerRequest request, CustomerDTO customerInRequest) {
        customerInRequest.getRoles().forEach(role->
        {
            MemberListDTO memberList = DTOConverter.convertMemberList(customerInRequest);
            userServiceWebClient.put().uri(uriBuilder -> uriBuilder.path(userPath)
                    .build(request.pathVariable(BASE_SITE_ID), role))
                    .header(AUTHORIZATION,request.headers().header(AUTHORIZATION).get(0))
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(memberList))
                    .retrieve();
        });

    }



}
