package sap.commerce.org.unitservice.client;

import sap.commerce.org.unitservice.dto.UnitDTO;

import java.util.List;

public interface UnitClient {
    List<UnitDTO> getUnitsForUser(String userId);
}
