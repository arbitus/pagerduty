package com.example.pagerduty.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.pagerduty.dto.ServiceResponseDto;
import com.example.pagerduty.exception.TooManyRequestsException;
import com.example.pagerduty.exception.UnauthorizedException;

import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Component
public class PagerDutyClient {

        private final WebClient webClient;

        public PagerDutyClient(WebClient.Builder builder,
                        @Value("${PAGERDUTY_API_TOKEN}") String token,
                        @Value("${PAGERDUTY_API_URL}") String baseUrl) {
                this.webClient = builder
                                .baseUrl(baseUrl)
                                .defaultHeader("Authorization", "Token token=" + token)
                                .defaultHeader("Accept", "application/vnd.pagerduty+json;version=2")
                                .build();
        }

        public ServiceResponseDto getServices(int limit, int offset) {
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
                                                                new RuntimeException("PagerDuty API server error")))
                                .bodyToMono(ServiceResponseDto.class)
                                .block();
        }
}