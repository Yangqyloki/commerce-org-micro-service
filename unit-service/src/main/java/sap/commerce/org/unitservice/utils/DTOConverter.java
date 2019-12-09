package sap.commerce.org.unitservice.utils;

import static sap.commerce.org.unitservice.constants.UnitServiceConstants.DEFAULT_PWD;

import java.util.List;

import sap.commerce.org.unitservice.dto.CustomerDTO;
import sap.commerce.org.unitservice.dto.MemberDTO;
import sap.commerce.org.unitservice.dto.MemberListDTO;
import sap.commerce.org.unitservice.dto.OccCustomerDTO;

public class DTOConverter {

    public static OccCustomerDTO convertCustomer(final CustomerDTO customer) {
        final OccCustomerDTO occCustomerDTO = new OccCustomerDTO();
        occCustomerDTO.setFirstName(customer.getFirstName());
        occCustomerDTO.setLastName(customer.getLastName());
        occCustomerDTO.setPassword(DEFAULT_PWD);
        occCustomerDTO.setTitleCode(customer.getTitle());
        occCustomerDTO.setUid(customer.getEmail());
        return occCustomerDTO;
    }

    public static MemberListDTO convertMemberList(final CustomerDTO customer) {
        final MemberDTO member = new MemberDTO();
        member.setName(customer.getLastName() + " " + customer.getFirstName());
        member.setUid(customer.getEmail());
        return new MemberListDTO(List.of(member));
    }
}
