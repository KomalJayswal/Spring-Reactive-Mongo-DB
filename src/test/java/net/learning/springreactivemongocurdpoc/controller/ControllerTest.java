package net.learning.springreactivemongocurdpoc.controller;

import net.learning.springreactivemongocurdpoc.dto.Dto;
import net.learning.springreactivemongocurdpoc.service.Service;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(Controller.class)
class ControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private Service service;

    @Test
    public void addProductTest(){
        Mono<Dto> DtoMono=Mono.just(new Dto("102","mobile",1,10000));
        when(service.saveProduct(DtoMono)).thenReturn(DtoMono);

        webTestClient.post().uri("/products")
                .body(Mono.just(DtoMono),Dto.class)
                .exchange()
                .expectStatus().isOk();//200

    }


    @Test
    public void getProductsTest(){
        Flux<Dto> DtoFlux=Flux.just(new Dto("102","mobile",1,10000),
                new Dto("103","TV",1,50000));
        when(service.getProducts()).thenReturn(DtoFlux);

        Flux<Dto> responseBody = webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Dto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new Dto("102","mobile",1,10000))
                .expectNext(new Dto("103","TV",1,50000))
                .verifyComplete();

    }


    @Test
    public void getProductTest(){
        Mono<Dto> DtoMono=Mono.just(new Dto("102","mobile",1,10000));
        when(service.getProduct(any())).thenReturn(DtoMono);

        Flux<Dto> responseBody = webTestClient.get().uri("/products/102")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Dto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getName().equals("mobile"))
                .verifyComplete();
    }


    @Test
    public void updateProductTest(){
        Mono<Dto> DtoMono=Mono.just(new Dto("102","mobile",1,10000));
        when(service.updateProduct(DtoMono,"102")).thenReturn(DtoMono);

        webTestClient.put().uri("/products/update/102")
                .body(Mono.just(DtoMono),Dto.class)
                .exchange()
                .expectStatus().isOk();//200
    }

    @Test
    public void deleteProductTest(){
        given(service.deleteProduct(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/products/delete/102")
                .exchange()
                .expectStatus().isOk();//200
    }

}
