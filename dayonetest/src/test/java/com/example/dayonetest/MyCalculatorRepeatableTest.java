package com.example.dayonetest;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MyCalculatorRepeatableTest {

    public static Stream<Arguments> parameterizedTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 10.0),
                Arguments.of(4.0, 4.0),
                Arguments.of(12.0, 12.0),
                Arguments.of(13.0, 13.0),
                Arguments.of(1.0, 1.0)
        );
    }

    public static Stream<Arguments> parameterizedComplicatedTest() {
        return Stream.of(
                Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0),
                Arguments.of(4.0, 2.0, 4.0, 4.0, 2.0)
        );
    }

    @RepeatedTest(5)
    public void repeatedAddTest() {
        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator();

        // Act - 행동
        myCalculator.add(10.0);

        // Assert - 단언/검증
        Assertions.assertEquals(10.0, myCalculator.getResult());
    }

    @ParameterizedTest
    @MethodSource("parameterizedTestParameters")
    public void parameterizedTest(Double addValue, Double expectValue) {
        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator(0.0);

        // Act - 행동
        myCalculator.add(addValue);

        // Assert - 단언/검증
        Assertions.assertEquals(expectValue, myCalculator.getResult());
    }

    @ParameterizedTest
    @MethodSource("parameterizedComplicatedTest")
    public void parameterizedComplicatedCalculateTest(
            Double addValue,
            Double minusValue,
            Double multiplyValue,
            Double divideValue,
            Double expectValue
    ) {
        //given
        MyCalculator myCalculator = new MyCalculator();

        //when
        Double result = myCalculator
                .add(addValue)
                .minus(minusValue)
                .multiply(multiplyValue)
                .divide(divideValue)
                .getResult();

        //then
        Assertions.assertEquals(expectValue, result);
    }
}
