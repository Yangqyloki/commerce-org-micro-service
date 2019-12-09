package sap.commerce.org.unitservice.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "b2b_commerce_org.unit")
public class UnitDTO extends AbstractDTO {

    private String unitId;

    private String unitName;

    private String parentUnit;

    private String approvalProcess;

    private String path;

    private List<CustomerDTO> unitCustomers;

    private CustomerDTO administrator;

    @Override
    public boolean checkIfDTOEmpty() {
        return StringUtils.isBlank(this.unitId) || StringUtils.isBlank(this.unitName)
            || StringUtils.isBlank(this.parentUnit) || StringUtils.isBlank(this.approvalProcess);
    }
}
