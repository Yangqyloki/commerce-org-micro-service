package sap.commerce.org.userservice.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO {
    public final static String CREATE_USER = "createUser";

    @JsonProperty
    private String type;

    @JsonProperty
    private Object message;

}
