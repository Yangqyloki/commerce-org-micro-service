package sap.commerce.org.unitservice.dao;

import java.util.List;

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

@Component
public class UnitDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UnitDTO> getUnitsByUser(final String userId) {
        final UnitUserDTO unitUser = getUnitUser(userId);
//        System.out.println("UnitDao userUnit: " + unitUser);
        if(unitUser == null || StringUtils.isBlank(unitUser.getUnitId())){
            return List.of(new UnitDTO());
        }
        final Query queryUnits = new Query();
//        String regex = "^(\\/"+ unitUser.getUnitId()+")[\\s\\S]+";
//        System.out.println("regex: " + regex);
        queryUnits.addCriteria(Criteria.where("path").regex("^(\\/"+ unitUser.getUnitId()+")"));
        final List<UnitDTO> userUnits =  mongoTemplate.find(queryUnits, UnitDTO.class,"b2b_commerce_org.unit");
//        userUnits.forEach(System.out::println);
        return userUnits;
    }

    public UnitUserDTO getUnitUser(final String userId) {
        final Query queryUserUnit = new Query();
        queryUserUnit.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(queryUserUnit, UnitUserDTO.class, "b2b_commerce_org.unitUser");
    }


    public List<UnitDTO> findAllUnits() {
        return mongoTemplate.findAll(UnitDTO.class, "b2b_commerce_org.unit");
    }

    public UnitDTO createUnit(final UnitDTO unit) {
        final Query queryParent = new Query();
        queryParent.addCriteria(Criteria.where("unitId").is(unit.getParentUnit()));
        final UnitDTO parent = mongoTemplate.findOne(queryParent, UnitDTO.class, "b2b_commerce_org.unit");
        unit.setPath(parent.getPath() + "/" + unit.getUnitId());
        mongoTemplate.save(unit, "b2b_commerce_org.unit");
        return unit;
    }

    public UnitDTO getUnitByUnitId(final String unitId) {
        final Query queryUnit = new Query();
        queryUnit.addCriteria(Criteria.where("unitId").is(unitId));
        return mongoTemplate.findOne(queryUnit, UnitDTO.class, "b2b_commerce_org.unit");
    }

    public void saveUnitCustomer(final String unitId, final CustomerDTO customer) {
        final Query queryUnit = new Query();
        queryUnit.addCriteria(Criteria.where("unitId").is(unitId));
        final Update update = new Update().push("customer", customer);
        mongoTemplate.upsert(queryUnit, update, UnitDTO.class, "b2b_commerce_org.unit");
    }
}
