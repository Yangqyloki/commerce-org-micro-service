package sap.commerce.org.unitservice.repository;

import sap.commerce.org.unitservice.dto.UnitDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitDTO,String> {

}
