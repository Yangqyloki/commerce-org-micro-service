package sap.commerce.org.unitservice.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "b2b_commerce_org.unit")
public class UnitDTO {
    private String unitId;

    private String unitName;

    private String parentUnit;

    private String approvalProcess;

    private String path;

    private List<CustomerDTO> unitCustomers;

    private CustomerDTO administrator;
}
