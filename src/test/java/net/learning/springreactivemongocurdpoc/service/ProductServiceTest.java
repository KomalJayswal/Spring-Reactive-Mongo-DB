package net.learning.springreactivemongocurdpoc.service;

import net.learning.springreactivemongocurdpoc.dto.Product;
import net.learning.springreactivemongocurdpoc.entity.ProductEntity;
import net.learning.springreactivemongocurdpoc.exceptions.ProductException;
import net.learning.springreactivemongocurdpoc.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void getProductErrorTest() {
        Mockito.when(productRepository.findById("test_id")).thenReturn(Mono.empty());
        Mono<Product> bookingDomainMono = productService.retriveProduct("test_id");
        StepVerifier.create(bookingDomainMono).expectError(ProductException.class).verify();
    }

    @Test
    void retrieveBookingTest() {
        String productId = "test_id";

        Mono<ProductEntity> product = Mono.just(new ProductEntity("test_id","mobile",1,10000));

        Mockito.when(productRepository.findById("test_id")).thenReturn(product);
        Mono<Product> bookingResponse = productService.retriveProduct(productId);
        StepVerifier.create(bookingResponse)
                .expectSubscription()
                .assertNext(e -> Assertions.assertEquals(productId, e.getId()))
                .verifyComplete();
    }
}
