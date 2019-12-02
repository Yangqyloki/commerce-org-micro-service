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
    public static final String UNIT_SERVICE_UNITS = UNIT_SERVICE_PREFIX + "{userId}/units";
    public static final String UNIT_SERVICE_NEW_UNIT = UNIT_SERVICE_UNITS + "/{unitId}";
    public static final String UNIT_SERVICE_NEW_CUSTOMER = UNIT_SERVICE_NEW_UNIT + "/customers/{customerId}";

    @Bean
    public RouterFunction<ServerResponse> unitRouters(final UnitServiceHandler unitServiceHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(UNIT_SERVICE_UNITS), unitServiceHandler::getUnits)
                .andRoute(RequestPredicates.POST(UNIT_SERVICE_NEW_UNIT).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), unitServiceHandler::createUnit)
                .andRoute(RequestPredicates.POST(UNIT_SERVICE_NEW_CUSTOMER).and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), unitServiceHandler::createCustomerForUnit);
    }
}
