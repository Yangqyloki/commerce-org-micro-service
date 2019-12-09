package sap.commerce.org.unitservice.client.impl;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.AUTHORIZATION;
import static sap.commerce.org.unitservice.constants.UnitServiceConstants.BASE_SITE_ID;
import static sap.commerce.org.unitservice.constants.UnitServiceConstants.USER_ID;

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
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.MemberListDTO;
import sap.commerce.org.unitservice.dto.OccCustomerDTO;
import sap.commerce.org.unitservice.dto.UserGroupListDTO;
import sap.commerce.org.unitservice.dto.utils.DTOConverter;

@Component
public class DefaultUserClient implements UserClient {

    @Value("${user.service.usergroups.path}")
    private String userGroupsPath;

    @Value("${user.service.user.path}")
    private String userPath;

    @Value("${user.service.users.path}")
    private String usersPath;

    @Value("${user.service.usergroup.set.path}")
    private String saveUserGroupPath;

    @Autowired
    private WebClient userServiceWebClient;

    @Autowired
    private UnitDao unitDao;

    @Override
    public Mono<OccCustomerDTO> getCustomerById(final ServerRequest request) {
        return userServiceWebClient.get()
            .uri(uriBuilder -> uriBuilder.path(userPath).build(request.pathVariable(BASE_SITE_ID),
                request.pathVariable(USER_ID)))
            .header(AUTHORIZATION, request.headers().header(AUTHORIZATION).get(0)).accept(MediaType.APPLICATION_JSON)
            .retrieve().bodyToMono(OccCustomerDTO.class);
    }

    @Override
    public Mono<UserGroupListDTO> getUserGroups(final ServerRequest request) {
        return userServiceWebClient.get()
            .uri(uriBuilder -> uriBuilder.path(userGroupsPath).build(request.pathVariable(BASE_SITE_ID),
                request.pathVariable(USER_ID)))
            .header(AUTHORIZATION, request.headers().header(AUTHORIZATION).get(0)).accept(MediaType.APPLICATION_JSON)
            .retrieve().bodyToMono(UserGroupListDTO.class);
    }

    @Override
    public Mono<OccCustomerDTO> createCustomer(final ServerRequest request, final OccCustomerDTO occCustomer) {
        return userServiceWebClient.post()
            .uri(uriBuilder -> uriBuilder.path(usersPath).build(request.pathVariable(BASE_SITE_ID)))
            .header(AUTHORIZATION, request.headers().header(AUTHORIZATION).get(0)).accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(occCustomer)).retrieve().bodyToMono(OccCustomerDTO.class);
    }

    @Override
    public void setCustomerToUserGroup(final ServerRequest request, final CustomerDTO customerInRequest) {
        customerInRequest.getRoles().forEach(role -> {
            final MemberListDTO memberList = DTOConverter.convertMemberList(customerInRequest);
            userServiceWebClient.put()
                .uri(uriBuilder -> uriBuilder.path(usersPath).build(request.pathVariable(BASE_SITE_ID), role))
                .header(AUTHORIZATION, request.headers().header(AUTHORIZATION).get(0))
                .accept(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(memberList)).retrieve();
        });

    }

}
