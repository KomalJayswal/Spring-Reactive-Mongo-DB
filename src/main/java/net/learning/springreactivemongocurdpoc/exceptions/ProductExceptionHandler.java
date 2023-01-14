package net.learning.springreactivemongocurdpoc.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Exception handler for application.
 */
@Component
@Slf4j
public class ProductExceptionHandler implements ErrorWebExceptionHandler {

  public static <T> Mono<Void> writeErrorResponse(ServerHttpResponse httpResponse, T object) {
    return httpResponse.writeWith(
        Mono.fromSupplier(
            () -> {
              DataBufferFactory bufferFactory = httpResponse.bufferFactory();
              try {
                return bufferFactory.wrap(new ObjectMapper().writeValueAsBytes(object));
              } catch (Exception ex) {
                log.warn("Error writing response", ex);
                return bufferFactory.wrap(new byte[0]);
              }
            }));
  }

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ServerHttpResponse response = exchange.getResponse();
    if (ex instanceof ProductException) {
      status = ((ProductException) ex).getStatus();
    }
    response.setStatusCode(status);
    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return writeErrorResponse(
        exchange.getResponse(), Map.of("errorCode", "000000700", "errorMessage", ex.getMessage()));
  }
}
