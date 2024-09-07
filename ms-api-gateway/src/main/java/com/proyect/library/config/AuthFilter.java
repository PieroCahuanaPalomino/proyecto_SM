package com.proyect.library.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;

import com.proyect.library.dto.TokenDto;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{
	private WebClient.Builder webClient;
	
	public AuthFilter(WebClient.Builder webClient) {
		super(Config.class);
		this.webClient=webClient;
	}
	
	@Override
	public GatewayFilter apply(Config config) {
	    return (((exchange, chain) -> {
            log.info("IMPRIMIENDO EXCHANGE: ", exchange);
            log.info("IMPRIMIENDO CHAIN: ", chain);
	        if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
	            return onError(exchange, HttpStatus.BAD_REQUEST);
	        }

	        String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            log.info("IMPRIMIENDO : ", tokenHeader);

	        String[] chunks = tokenHeader.split(" ");
	        
	        if(chunks.length != 2 || !chunks[0].equals("Bearer")) {
	            return onError(exchange, HttpStatus.BAD_REQUEST);
	        }

	        return webClient.build()
	            .post()
	            .uri("http://ms-auth-service/auth/validate?token=" + chunks[1])
	            .retrieve()
	            .bodyToMono(TokenDto.class)
	            .map(t -> {
	                // Puedes añadir más lógica aquí si es necesario
	                return exchange;
	            })
	            .flatMap(chain::filter)
	            .onErrorResume(e -> {
	                // Manejo más específico del error
	                if (e instanceof WebClientResponseException) {
	                    WebClientResponseException webClientException = (WebClientResponseException) e;
	                    // Loguear el error con más detalles
	                    log.error("Error en la respuesta del WebClient: {}", webClientException.getMessage());
	                    return onError(exchange, HttpStatus.SERVICE_UNAVAILABLE);
	                }
	                return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
	            });
	    }));
	}

	
	public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status){
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	public static class Config{}
}
