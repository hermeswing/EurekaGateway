package octopus.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 사용자 Filter. JWT와 권한 설정을 할 수 있음.
 */
@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public static class Config {
        // 설정정보를 추가한다.
    }

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("[사용자 Filter.apply]");
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("[사용자 전 처리 Filter] Request ID :: {}", request.getId());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("[사용자 후 처리 Filter] Response Status :: {}", response.getStatusCode());
            }));
        });
    }
}
