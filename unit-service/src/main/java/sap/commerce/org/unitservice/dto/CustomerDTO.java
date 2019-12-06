package sap.commerce.org.unitservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {

    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String parentUnit;
    private List<String> roles;

}
