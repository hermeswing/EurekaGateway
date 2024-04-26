package octopus.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

/**
 * AbstractGatewayFilterFactory 와 같이 사용할 수 없음.
 * route 처리를 여기서 수행하면 Filter 처리를 할 수 없음.
 */
//@Configuration
public class FilterConfig {
    /**
     * 각 Service 호출 시 추가적인 데이터를 추가할 수 있음.
     */
    //@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/service01/**")
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                .addResponseHeader("first-response", "first-response-header"))
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/service02/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
