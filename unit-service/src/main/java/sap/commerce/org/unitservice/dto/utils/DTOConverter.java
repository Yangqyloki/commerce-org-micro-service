package sap.commerce.org.unitservice.dto.utils;

import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.MemberDTO;
import sap.commerce.org.unitservice.dto.MemberListDTO;
import sap.commerce.org.unitservice.dto.OccCustomerDTO;

import java.util.List;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.DEFAULT_PWD;

public class DTOConverter {

    public static OccCustomerDTO convertCustomer(CustomerDTO customer){
        OccCustomerDTO occCustomerDTO= new OccCustomerDTO();
        occCustomerDTO.setFirstName(customer.getFirstName());
        occCustomerDTO.setLastName(customer.getLastName());
        occCustomerDTO.setPassword(DEFAULT_PWD);
        occCustomerDTO.setTitleCode(customer.getTitle());
        occCustomerDTO.setUid(customer.getEmail());
        return occCustomerDTO;
    }

    public static MemberListDTO convertMemberList(CustomerDTO customer){
        MemberDTO member = new MemberDTO();
        member.setName(customer.getLastName() + " " + customer.getFirstName());
        member.setUid(customer.getEmail());
        return new MemberListDTO(List.of(member));
    }
}
