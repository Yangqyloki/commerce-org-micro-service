package sap.commerce.org.unitservice.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UserClient;
import sap.commerce.org.unitservice.dto.UserGroup;
import sap.commerce.org.unitservice.dto.UserGroups;

import java.util.List;

@Component
public class DefaultUserClient implements UserClient {

    private static final String AUTHORIZATION = "Authorization";

    @Value("${user.service.usergroups.path}")
    private String userGroupsPath;

    @Autowired
    private WebClient userServiceWebClient;

    @Override
    public Mono<UserGroups> getUserGroups( ServerRequest request) {


        String baseSiteId = request.pathVariable("baseSiteId");
        String userId = request.pathVariable("userId");
        Mono<UserGroups> test =  userServiceWebClient.get().uri(uriBuilder -> uriBuilder.path(userGroupsPath).build(baseSiteId, userId))
                .header(AUTHORIZATION,request.headers().header(AUTHORIZATION).get(0))
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UserGroups.class);

        test.subscribe(groups->System.out.println(groups));
        return test;
    }
}
