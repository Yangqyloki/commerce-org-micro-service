package sap.commerce.org.unitservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="unit")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class UnitDTO {
    @Id
    private String unitId;

    private String unitName;

    private String parentUnit;

    private String approvalProcess;
}
