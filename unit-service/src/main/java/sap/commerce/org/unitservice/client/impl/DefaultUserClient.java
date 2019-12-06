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
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.UnitDTO;
import sap.commerce.org.unitservice.dto.UnitUserDTO;
import sap.commerce.org.unitservice.dto.UserGroupDTO;

import java.util.List;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.*;

@Component
public class DefaultUserClient implements UserClient {


    @Value("${user.service.usergroups.path}")
    private String userGroupsPath;

    @Value("${user.service.user.path}")
    private String userPath;

    @Autowired
    private WebClient userServiceWebClient;

    @Autowired
    private UnitDao unitDao;

    //Need refactor
    @Override
    public Mono<UnitDTO> createCustomerForUnit(ServerRequest request) {
        List<UserGroupDTO> userGroups = userServiceWebClient.get().uri(uriBuilder -> uriBuilder.path(userGroupsPath).build(request.pathVariable(BASE_SITE_ID), request.pathVariable(USER_ID)))
                .header(AUTHORIZATION, request.headers().header(AUTHORIZATION).get(0))
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(UserGroupDTO.class).collectList().block();
        userGroups.forEach(System.out::println);
        UnitUserDTO unitUser = unitDao.getUnitUser(request.pathVariable(USER_ID));
        System.out.println(unitUser);
        if (isB2BAdmin(userGroups) && request.pathVariable(USER_ID).equals(unitUser.getUnitId())) {
            Mono<CustomerDTO> customer = userServiceWebClient.post().uri(uriBuilder -> uriBuilder.path(userGroupsPath)
                    .build(request.pathVariable(BASE_SITE_ID), request.pathVariable(USER_ID), request.pathVariable(UNIT_ID)))
                    .header(AUTHORIZATION, request.headers().header(AUTHORIZATION).get(0))
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject(request.bodyToMono(CustomerDTO.class).block()))
                    .retrieve().bodyToMono(CustomerDTO.class);
            unitDao.saveUnitCustomer(request.pathVariable(UNIT_ID), customer.block());

        }
        return Mono.just(unitDao.getUnitByUnitId(request.pathVariable(UNIT_ID)));
    }

    private boolean isB2BAdmin(List<UserGroupDTO> userGroups) {
        return userGroups.stream().filter(userGroup -> "asd".equals(userGroup.getUid())).findAny().isPresent();
    }
}
