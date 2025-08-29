package com.example.pagerduty.pagerduty.client;

import com.example.pagerduty.client.PagerDutyClient;
import com.example.pagerduty.dto.ServiceResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import java.util.function.Function;
import java.net.URI;

import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagerDutyClientTest {
    @Mock
    private WebClient.Builder webClientBuilder;

    @Test
    void testGetServicesWithAuthentication() {
        WebClient.RequestHeadersUriSpec<?> uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        WebClient webClient = Mockito.mock(WebClient.class);

        when(webClientBuilder.baseUrl(any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(eq("Authorization"), any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(eq("Accept"), any(String.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        Mockito.doReturn(uriSpec).when(webClient).get();
        Mockito.doReturn(headersSpec).when(uriSpec).uri(any(Function.class));
        Mockito.doReturn(responseSpec).when(headersSpec).retrieve();
        Mockito.doReturn(responseSpec).when(responseSpec).onStatus(any(), any());
        ServiceResponseDto expectedResponse = new ServiceResponseDto();
        Mockito.doReturn(Mono.just(expectedResponse)).when(responseSpec)
                .bodyToMono(ServiceResponseDto.class);

        // Instanciar el cliente con los mocks y valores dummy
        String dummyToken = "dummy-token";
        String dummyUrl = "http://dummy-url";
        PagerDutyClient client = new PagerDutyClient(webClientBuilder, dummyToken, dummyUrl);

        ServiceResponseDto result = client.getServices(10, 0);
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }
}
