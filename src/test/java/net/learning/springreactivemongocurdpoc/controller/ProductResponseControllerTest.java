package net.learning.springreactivemongocurdpoc.controller;

import net.learning.springreactivemongocurdpoc.dto.ProductRequest;
import net.learning.springreactivemongocurdpoc.dto.ProductResponse;
import net.learning.springreactivemongocurdpoc.mapper.ProductMapper;
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
class ProductResponseControllerTest {
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

        ProductRequest productRequest = new ProductRequest("mobile",1,10000);
        ProductResponse productResponse = new ProductResponse("101","mobile",1,10000);
        when(productService.saveProduct(productRequest)).thenReturn(Mono.just(productResponse));

        webTestClient
                .post()
                .uri("/products")
                .body(Mono.just(productResponse), ProductResponse.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }


    @Test
    public void getProductsTest(){
        Flux<ProductResponse> DtoFlux=Flux.just(new ProductResponse("102","mobile",1,10000),
                new ProductResponse("103","TV",1,50000));
        when(productService.getProducts()).thenReturn(DtoFlux);

        Flux<ProductResponse> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductResponse.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductResponse("102","mobile",1,10000))
                .expectNext(new ProductResponse("103","TV",1,50000))
                .verifyComplete();
    }


    @Test
    public void getProductTest(){
        Mono<ProductResponse> productResponse=Mono.just(new ProductResponse("102","mobile",1,10000));
        when(productService.retriveProduct(any())).thenReturn(productResponse);

        Flux<ProductResponse> responseBody = webTestClient.get().uri("/products/102")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductResponse.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getName().equals("mobile"))
                .verifyComplete();
    }


    @Test
    public void updateProductTest(){
        ProductRequest productRequest = new ProductRequest("mobile",1,10000);

        Mono<ProductResponse> productMono = Mono.just(new ProductResponse("102","mobile",1,10000));
        when(productService.updateProduct(productRequest,"102")).thenReturn(productMono);

        webTestClient.put().uri("/products/102")
                .body(Mono.just(productMono), ProductResponse.class)
                .exchange()
                .expectStatus().isOk();//200
    }

    @Test
    public void deleteProductTest(){
        given(productService.deleteProduct(any())).willReturn(Mono.just("Product is Deleted successfully !"));
        webTestClient.delete().uri("/products/102")
                .exchange()
                .expectStatus()
                .isOk();
    }

}
