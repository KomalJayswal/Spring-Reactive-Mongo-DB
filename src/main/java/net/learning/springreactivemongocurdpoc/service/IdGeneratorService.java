package net.learning.springreactivemongocurdpoc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * IDGeneratorService for application.
 */
@Service
@Slf4j
public class IdGeneratorService {

  public Mono<String> getProductId() {
    String productId = UUID.randomUUID().toString();
    log.info("Generated productId : {}", productId);
    return Mono.just(productId);
  }
}
