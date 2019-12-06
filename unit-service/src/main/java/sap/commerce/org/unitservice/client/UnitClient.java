package sap.commerce.org.unitservice.client;

import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.dto.UnitDTO;

import java.util.List;

public interface UnitClient {

    Mono<List<UnitDTO>> getUnitsByUser(String userId);

    Mono<UnitDTO> creatUnit(String userId, UnitDTO unit);
}
