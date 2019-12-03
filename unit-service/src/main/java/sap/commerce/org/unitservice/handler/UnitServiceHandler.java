package sap.commerce.org.unitservice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UserClient;

@Component
public class UnitServiceHandler {
    @Autowired
    private UserClient userClient;

    public Mono<ServerResponse> getUnits(final ServerRequest request){
        return userClient.getUserGroups("electronics","linda.wolf@rustic-hw.com").
                flatMap(userGroups -> EntityResponse.fromObject(userGroups).contentType(MediaType.APPLICATION_JSON).
                        status(HttpStatus.OK).build());
    }
    public Mono<ServerResponse> createUnit(final ServerRequest request){
        return null;
    }
    public Mono<ServerResponse> createCustomerForUnit(final ServerRequest request){
        return null;
    }
}
