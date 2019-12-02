package sap.commerce.org.unitservice.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UnitServiceHandler {
    public Mono<ServerResponse> getUnits(final ServerRequest request){
        return null;
    }
    public Mono<ServerResponse> createUnit(final ServerRequest request){
        return null;
    }
    public Mono<ServerResponse> createCustomerForUnit(final ServerRequest request){
        return null;
    }
}
