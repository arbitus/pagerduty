package com.example.pagerduty.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.example.pagerduty.dto.ServiceResponseDto;
import com.example.pagerduty.exception.TooManyRequestsException;
import com.example.pagerduty.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * Client for interacting with PagerDuty API.
 */
@Component
public class PagerDutyClient {

        private static final String AUTH_HEADER = "Authorization";
        private static final String ACCEPT_HEADER = "Accept";
        private static final String AUTH_PREFIX = "Token token=";
        private static final String ACCEPT_VALUE = "application/vnd.pagerduty+json;version=2";

        private final WebClient webClient;

        public PagerDutyClient(WebClient.Builder builder,
                        @Value("${PAGERDUTY_API_TOKEN}") String token,
                        @Value("${PAGERDUTY_API_URL}") String baseUrl) {
                this.webClient = builder
                                .baseUrl(baseUrl)
                                .defaultHeader(AUTH_HEADER, AUTH_PREFIX + token)
                                .defaultHeader(ACCEPT_HEADER, ACCEPT_VALUE)
                                .build();
        }

        /**
         * Fetches services from PagerDuty API.
         * 
         * @param limit  number of items per page (should be > 0)
         * @param offset offset for pagination (should be >= 0)
         * @return ServiceResponseDto with service data
         */
        public ServiceResponseDto getServices(int limit, int offset) {
                if (limit <= 0) {
                        throw new IllegalArgumentException("Limit must be greater than 0");
                }
                if (offset < 0) {
                        throw new IllegalArgumentException("Offset must be non-negative");
                }
                try {
                        return webClient.get()
                                        .uri(uriBuilder -> uriBuilder.path("/services")
                                                        .queryParam("limit", limit)
                                                        .queryParam("offset", offset)
                                                        .build())
                                        .retrieve()
                                        .onStatus(status -> status == HttpStatus.UNAUTHORIZED,
                                                        response -> Mono.error(new UnauthorizedException(
                                                                        "Unauthorized to access PagerDuty API")))
                                        .onStatus(status -> status == HttpStatus.TOO_MANY_REQUESTS,
                                                        response -> Mono.error(new TooManyRequestsException(
                                                                        "Rate limit exceeded for PagerDuty API")))
                                        .onStatus(status -> status.is5xxServerError(),
                                                        response -> Mono.error(
                                                                        new RuntimeException(
                                                                                        "PagerDuty API server error")))
                                        .bodyToMono(ServiceResponseDto.class)
                                        .block();
                } catch (WebClientResponseException e) {
                        throw new RuntimeException("Error calling PagerDuty API: " + e.getMessage(), e);
                }
        }
}