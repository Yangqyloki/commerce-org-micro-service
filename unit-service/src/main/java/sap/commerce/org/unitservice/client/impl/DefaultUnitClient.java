package sap.commerce.org.unitservice.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sap.commerce.org.unitservice.client.UnitClient;
import sap.commerce.org.unitservice.dto.UnitDTO;
import sap.commerce.org.unitservice.repository.UnitRepository;

import java.util.List;

@Component
public class DefaultUnitClient implements UnitClient {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public List<UnitDTO> getUnitsForUser(String userId) {
        return unitRepository.findAll();
    }
}
