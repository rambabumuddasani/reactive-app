package com.reactive.learn.fluxmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                //.concatWith(Flux.error(new RuntimeException("Flux runtime exception")))
                ;
        flux = flux.concatWith(Flux.just("after error")).log();
        flux.subscribe(System.out::println, e -> System.err.println(e), () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestWithOutError() {
        Flux<String> flux = Flux.just("Spring", "Spring Boot", "Reactive Spring").log();
        StepVerifier.create(flux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete()// equalent to subscribe , without this method Flux will not invoke Flux flow.
        ;
        //      flux.subscribe(System.out::println,e -> System.err.println(e),() -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestWithError() {
        Flux<String> flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Flux with RuntimeException")))
                .log();
        StepVerifier.create(flux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Flux with RuntimeException")
                .verify(); // equalent to subscribe , without this method Flux will not invoke Flux flow.
        ;
        //      flux.subscribe(System.out::println,e -> System.err.println(e),() -> System.out.println("Completed"));
    }

    @Test
    public void fluxTesElements_WithCount() {
        Flux<String> flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Flux with RuntimeException")))
                .log();
        StepVerifier.create(flux)
                .expectNextCount(3)
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Flux with RuntimeException")
                .verify(); // equalent to subscribe , without this method Flux will not invoke Flux flow.
        ;
        //      flux.subscribe(System.out::println,e -> System.err.println(e),() -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestWithErrorOtherWay() {
        Flux<String> flux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Flux with RuntimeException")))
                .log();
        StepVerifier.create(flux)
                .expectNext("Spring","Spring Boot","Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Flux with RuntimeException")
                .verify(); // equalent to subscribe , without this method Flux will not invoke Flux flow.
        ;
        //      flux.subscribe(System.out::println,e -> System.err.println(e),() -> System.out.println("Completed"));
    }

    @Test
    public void monoTest(){
        Mono<String> monoSpring = Mono.just("Spring").log();
        //monoSpring.subscribe(e-> System.out.println(e));
        StepVerifier.create(monoSpring)
  //              .expectNextCount(1)
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_withError(){
        Mono<String> monoSpring = Mono.error(new RuntimeException("Runtime Exception for Mono"));
        //monoSpring.subscribe(e-> System.out.println(e));
        StepVerifier.create(monoSpring)
                .expectErrorMessage("Runtime Exception for Mono")
               // .expectError(RuntimeException.class)
                .verify();
    }
}
