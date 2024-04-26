package octopus.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Filter Configuration 과 같이 처리 할 수 없음.
 *
 */
@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("[GlobalFilter.apply] 여기..");
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("[Global Filter] BaseMessage: {}, RemoteAddress : {}", config.getBaseMessage(), request.getRemoteAddress());

            if (config.isPreLogger()) {
                log.info("[Global Filter] Start: Request ID -> {}", request.getId());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("[Global Filter] End: Response code -> {}", response.getStatusCode());
                }
            }));
        });
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
