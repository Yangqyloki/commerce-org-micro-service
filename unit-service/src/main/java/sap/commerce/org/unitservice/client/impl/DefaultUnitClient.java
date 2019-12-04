package sap.commerce.org.unitservice.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sap.commerce.org.unitservice.client.UnitClient;
import sap.commerce.org.unitservice.dto.UnitDTO;
import sap.commerce.org.unitservice.repository.UnitRepository;

import java.util.List;

@Component
public class DefaultUnitClient implements UnitClient {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public Mono<UnitDTO> creatUnit(String userId, UnitDTO unit) {
        List<UnitDTO> units = unitRepository.findAll();
        if(units.stream().parallel().filter(u -> u.getUnitId().equals(unit.getUnitId())).findAny().isPresent()){
            System.out.println("This unit id [" + unit.getUnitId() + "] is already in use!");
        } else if(units.stream().parallel().filter(u -> u.getUnitId().equals(unit.getParentUnit())).findAny().isEmpty()) {
            System.out.println("This parent unit [" + unit.getUnitId() + "] is not found!");
        } else {
            unitRepository.save(unit);
        }
        return Mono.just(unit);
    }
}
