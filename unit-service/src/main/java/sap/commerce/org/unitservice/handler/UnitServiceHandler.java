package sap.commerce.org.unitservice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UnitClient;
import sap.commerce.org.unitservice.client.UserClient;
import sap.commerce.org.unitservice.dto.UnitDTO;

import java.util.List;

@Component
public class UnitServiceHandler {

    @Autowired
    private UserClient userClient;

    @Autowired
    private UnitClient unitClient;

    public Mono<ServerResponse> getUnits(final ServerRequest request){
        System.out.println("getUnits!!!!");
        return userClient.getUserGroups(request).
                flatMap(userGroups -> EntityResponse.fromObject(userGroups).contentType(MediaType.APPLICATION_JSON).
                        status(HttpStatus.OK).build());
    }

    public Mono<ServerResponse> createUnit(final ServerRequest request){
        System.out.println("createUnit!!!!");
        List<UnitDTO> units = unitClient.getUnitsForUser("linda.wolf@rustic-hw.com");
        units.forEach(unit -> System.out.println(unit));
        return null;
    }

    public Mono<ServerResponse> createCustomerForUnit(final ServerRequest request){
        return null;
    }
}
