package sap.commerce.org.unitservice.client;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.dto.UnitDTO;


public interface UserClient {
    Mono<UnitDTO> createCustomerForUnit(ServerRequest request);
}
