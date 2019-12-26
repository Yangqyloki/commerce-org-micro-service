package sap.commerce.org.unitservice.dto;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO extends AbstractDTO {

    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String parentUnit;
    private List<String> roles;

    @Override
    public boolean checkIfDTOEmpty() {
        return StringUtils.isBlank(this.firstName) || StringUtils.isBlank(this.lastName)
            || StringUtils.isBlank(this.title) || StringUtils.isBlank(this.email) || Objects.isNull(this.parentUnit)
            || CollectionUtils.isEmpty(roles);
    }
}
