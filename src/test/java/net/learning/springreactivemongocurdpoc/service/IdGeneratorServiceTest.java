package net.learning.springreactivemongocurdpoc.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class IdGeneratorServiceTest {

    @InjectMocks private IdGeneratorService idGeneratorService;

    @Test
    void testGetBookingId() {
        StepVerifier.create(idGeneratorService.getProductId())
                .expectSubscription()
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }
}