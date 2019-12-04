package sap.commerce.org.unitservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="unit")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("hibernateLazyInitializer")
public class UnitDTO {
    @Id
    private String unitId;

    private String unitName;

    private String parentUnit;

    private String approvalProcess;
}
