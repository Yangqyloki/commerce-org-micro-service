package sap.commerce.org.unitservice.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UnitClient;
import sap.commerce.org.unitservice.dao.UnitDao;
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.UnitDTO;

import java.util.List;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.UNIT_ID;


@Component
public class DefaultUnitClient implements UnitClient {

//    @Autowired
//    private UnitRepository unitRepository;

    @Autowired
    private UnitDao unitDao;


    @Override
    public Mono<List<UnitDTO>> getUnitsByUser(String userId) {
        return Mono.just(unitDao.getUnitsByUser(userId));
    }

    @Override
    public Mono<UnitDTO> creatUnit(String userId, UnitDTO unit) {
        List<UnitDTO> units = unitDao.findAllUnits();
        if(units.stream().parallel().filter(u -> u.getUnitId().equals(unit.getUnitId())).findAny().isPresent()){
            System.out.println("This unit id [" + unit.getUnitId() + "] is already in use!");
        } else if(units.stream().parallel().filter(u -> u.getUnitId().equals(unit.getParentUnit())).findAny().isEmpty()) {
            System.out.println("This parent unit [" + unit.getUnitId() + "] is not found!");
        }
            return Mono.just(unitDao.createUnit(unit));
    }

    @Override
    public Mono<UnitDTO> createCustomerForUnit(String unitId,CustomerDTO customer) {
        unitDao.saveUnitCustomer(unitId,customer);
        return Mono.just(unitDao.getUnitByUnitId(unitId));
    }
}
