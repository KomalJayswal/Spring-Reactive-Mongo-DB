package net.learning.springreactivemongocurdpoc.controller;

import net.learning.springreactivemongocurdpoc.dto.Product;
import net.learning.springreactivemongocurdpoc.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ProductControllerTest {
    private WebTestClient webTestClient;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToController(productController).build();
    }

    @Test
    public void addProductTest(){

        Product product = new Product("102","mobile",1,10000);
        when(productService.saveProduct(product)).thenReturn(Mono.just(product));

        webTestClient
                .post()
                .uri("/products")
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }


    @Test
    public void getProductsTest(){
        Flux<Product> DtoFlux=Flux.just(new Product("102","mobile",1,10000),
                new Product("103","TV",1,50000));
        when(productService.getProducts()).thenReturn(DtoFlux);

        Flux<Product> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new Product("102","mobile",1,10000))
                .expectNext(new Product("103","TV",1,50000))
                .verifyComplete();
    }


    @Test
    public void getProductTest(){
        Mono<Product> DtoMono=Mono.just(new Product("102","mobile",1,10000));
        when(productService.retriveProduct(any())).thenReturn(DtoMono);

        Flux<Product> responseBody = webTestClient.get().uri("/products/102")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getName().equals("mobile"))
                .verifyComplete();
    }


    @Test
    public void updateProductTest(){
        Mono<Product> productMono = Mono.just(new Product("102","mobile",1,10000));
        when(productService.updateProduct("102")).thenReturn(productMono);

        webTestClient.put().uri("/products/update/102")
                .body(Mono.just(productMono), Product.class)
                .exchange()
                .expectStatus().isOk();//200
    }

    @Test
    public void deleteProductTest(){
        given(productService.deleteProduct(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/products/delete/102")
                .exchange()
                .expectStatus().isOk();//200
    }

}
