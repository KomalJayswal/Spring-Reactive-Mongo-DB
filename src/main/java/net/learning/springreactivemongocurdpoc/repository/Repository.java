package net.learning.springreactivemongocurdpoc.repository;

import net.learning.springreactivemongocurdpoc.dto.Dto;
import net.learning.springreactivemongocurdpoc.entity.Entity;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

@org.springframework.stereotype.Repository
public interface Repository extends ReactiveMongoRepository<Entity,String> {
    Flux<Dto> findByPriceBetween(Range<Double> priceRange);
}