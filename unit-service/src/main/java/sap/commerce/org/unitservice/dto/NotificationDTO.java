package sap.commerce.org.unitservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO {
    public final static String CREATE_USER = "createUser";

    private String type;
    private Object message;

}
