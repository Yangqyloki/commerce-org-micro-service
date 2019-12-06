package sap.commerce.org.unitservice.dao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.UnitDTO;
import sap.commerce.org.unitservice.dto.UnitUserDTO;

import java.util.List;

@Component
public class UnitDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UnitDTO> getUnitsByUser(String userId) {
        UnitUserDTO unitUser = getUnitUser(userId);
//        System.out.println("UnitDao userUnit: " + userUnit);
        if (unitUser == null || StringUtils.isBlank(unitUser.getUnitId())) {
            return List.of(new UnitDTO());
        }
        Query queryUnits = new Query();
//        String regex = "^(\\/"+ userUnit.getUnitId()+")[\\s\\S]+";
//        System.out.println("regex: " + regex);
        queryUnits.addCriteria(Criteria.where("path").regex("^(\\/" + unitUser.getUnitId() + ")"));
        List<UnitDTO> userUnits = mongoTemplate.find(queryUnits, UnitDTO.class, "b2b_commerce_org.unit");
        return userUnits;
    }

    public UnitUserDTO getUnitUser(String userId) {
        Query queryUserUnit = new Query();
        queryUserUnit.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(queryUserUnit, UnitUserDTO.class, "b2b_commerce_org.unitUser");
    }


    public List<UnitDTO> findAllUnits() {
        return mongoTemplate.findAll(UnitDTO.class, "b2b_commerce_org.unit");
    }

    public UnitDTO createUnit(UnitDTO unit) {
        Query queryParent = new Query();
        queryParent.addCriteria(Criteria.where("unitId").is(unit.getParentUnit()));
        UnitDTO parent = mongoTemplate.findOne(queryParent, UnitDTO.class, "b2b_commerce_org.unit");
        unit.setPath(parent.getPath() + "/" + unit.getUnitId());
        mongoTemplate.save(unit, "b2b_commerce_org.unit");
        return unit;
    }

    public UnitDTO getUnitByUnitId(String unitId) {
        Query queryUnit = new Query();
        queryUnit.addCriteria(Criteria.where("unitId").is(unitId));
        return mongoTemplate.findOne(queryUnit, UnitDTO.class, "b2b_commerce_org.unit");
    }

    public void saveUnitCustomer(String unitId, CustomerDTO customer) {
        Query queryUnit = new Query();
        queryUnit.addCriteria(Criteria.where("unitId").is(unitId));
        Update update = new Update().push("customer", customer);
        mongoTemplate.upsert(queryUnit, update, UnitDTO.class, "b2b_commerce_org.unit");
    }
}
