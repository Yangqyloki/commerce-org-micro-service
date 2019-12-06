package sap.commerce.org.unitservice.client;

import java.util.List;

import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.UnitDTO;

public interface UnitClient {

    Mono<List<UnitDTO>> getUnitsByUser(String userId);

    Mono<UnitDTO> creatUnit(String userId, UnitDTO unit);

    Mono<UnitDTO> createCustomerForUnit(String userId, CustomerDTO customer);
}
