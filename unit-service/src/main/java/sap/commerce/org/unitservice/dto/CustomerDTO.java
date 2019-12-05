package sap.commerce.org.unitservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String parentUnit;
    private List<String> Roles;

}
