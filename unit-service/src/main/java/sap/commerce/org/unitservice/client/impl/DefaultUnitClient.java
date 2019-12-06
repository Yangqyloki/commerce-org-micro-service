package sap.commerce.org.unitservice.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UnitClient;
import sap.commerce.org.unitservice.dao.UnitDao;
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.UnitDTO;


@Component
public class DefaultUnitClient implements UnitClient {

//    @Autowired
//    private UnitRepository unitRepository;

    @Autowired
    private UnitDao unitDao;


    @Override
    public Mono<List<UnitDTO>> getUnitsByUser(final String userId) {
        return Mono.just(unitDao.getUnitsByUser(userId));
    }

    @Override
    public Mono<UnitDTO> creatUnit(final String userId, final UnitDTO unit) {
        final List<UnitDTO> units = unitDao.findAllUnits();
        if (units.stream().parallel().filter(u -> u.getUnitId().equals(unit.getUnitId())).findAny().isPresent()) {
            System.out.println("This unit id [" + unit.getUnitId() + "] is already in use!");
        } else if (units.stream().parallel().filter(u -> u.getUnitId().equals(unit.getParentUnit())).findAny().isEmpty()) {
            System.out.println("This parent unit [" + unit.getUnitId() + "] is not found!");
        }
        return Mono.just(unitDao.createUnit(unit));
    }

    @Override
    public Mono<UnitDTO> createCustomerForUnit(final String unitId, final CustomerDTO customer) {
        unitDao.saveUnitCustomer(unitId,customer);
        return Mono.just(unitDao.getUnitByUnitId(unitId));
    }
}
