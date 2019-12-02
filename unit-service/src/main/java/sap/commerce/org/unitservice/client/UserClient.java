package sap.commerce.org.unitservice.client;

import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.dto.UserGroup;

import java.util.List;

public interface UserClient {
    Mono<List<UserGroup>> getUserGroups(String baseSiteId,String userId);
}
