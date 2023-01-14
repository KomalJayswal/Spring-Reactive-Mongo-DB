package net.learning.springreactivemongocurdpoc.repository;

import net.learning.springreactivemongocurdpoc.dto.ProductResponse;
import net.learning.springreactivemongocurdpoc.entity.ProductEntity;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity,String> {
    Flux<ProductEntity> findByPriceBetween(Range<Double> priceRange);
}