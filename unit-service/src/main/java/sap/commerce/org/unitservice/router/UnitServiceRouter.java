package sap.commerce.org.unitservice.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import sap.commerce.org.unitservice.handler.UnitServiceHandler;

@Configuration
public class UnitServiceRouter {
    public static final String UNIT_SERVICE_PREFIX = "/unitservice/v1/";
    public static final String UNIT_SERVICE_UNITS = UNIT_SERVICE_PREFIX + "{baseSiteId}/users/{userId}/units";
    public static final String UNIT_SERVICE_CREATE_CUSTOMER = UNIT_SERVICE_UNITS + "/{unitId}/customers";

    @Bean
    public RouterFunction<ServerResponse> unitRouters(final UnitServiceHandler unitServiceHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(UNIT_SERVICE_UNITS), unitServiceHandler::getUnitsByUser)
                .andRoute(RequestPredicates.POST(UNIT_SERVICE_UNITS).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), unitServiceHandler::createUnit)
                .andRoute(RequestPredicates.POST(UNIT_SERVICE_CREATE_CUSTOMER).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), unitServiceHandler::createCustomerForUnit);
    }
}
