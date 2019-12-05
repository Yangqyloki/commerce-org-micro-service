package sap.commerce.org.unitservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OccCustomerDTO {

    private String uid;
    private String firstName;
    private String lastName;
    private String password;
    private String titleCode;

}
