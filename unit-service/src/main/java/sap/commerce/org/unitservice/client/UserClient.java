package sap.commerce.org.unitservice.client;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.dto.UserGroup;
import sap.commerce.org.unitservice.dto.UserGroups;

import java.util.List;

public interface UserClient {
    Mono<UserGroups> getUserGroups(ServerRequest request);
}
