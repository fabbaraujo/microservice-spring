package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class MultiplicationServiceImplTest {

    @Mock
    private RandomGeneratorService randomGeneratorService;
    private MultiplicationServiceImpl multiplicationServiceImpl;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplicationTest() {
        //given
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
        User user = new User("john_doe");

        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 1500);

        //then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
        assertThat(attempt.getResultAttempt()).isEqualTo(1500);
    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000);

        //when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

        //assert
        assertThat(attemptResult).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010);

        //when
        boolean attempResult = multiplicationServiceImpl.checkAttempt(attempt);

        //assert
        assertThat(attempResult).isFalse();
    }
}
