package sap.commerce.org.unitservice.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UserClient;
import sap.commerce.org.unitservice.dto.UserGroup;

import java.util.List;

@Component
public class DefaultUserClient implements UserClient {

    @Value("${user.service.usergroups.path}")
    private String userGroupsPath;

    @Autowired
    private WebClient userServiceWebClient;

    @Override
    public Mono<List<UserGroup>> getUserGroups(String baseSiteId, String userId) {

        return userServiceWebClient.get().uri(uriBuilder -> uriBuilder.path(userGroupsPath).build(baseSiteId, userId)).
                accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(UserGroup.class).collectList();
    }
}
