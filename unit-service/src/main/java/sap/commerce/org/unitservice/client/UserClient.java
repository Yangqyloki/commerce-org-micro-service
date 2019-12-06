package sap.commerce.org.unitservice.client;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.OccCustomerDTO;
import sap.commerce.org.unitservice.dto.UserGroupListDTO;


public interface UserClient {
    Mono<UserGroupListDTO> getUserGroups(ServerRequest request);

    Mono<OccCustomerDTO> createCustomer(ServerRequest request,OccCustomerDTO occCustomer);

    void setCustomerToUserGroup(ServerRequest request, CustomerDTO customerInRequest);
}
