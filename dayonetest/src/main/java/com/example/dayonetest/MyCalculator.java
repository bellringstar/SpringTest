package com.example.dayonetest;

public class MyCalculator {

    private double result;

    public MyCalculator() {
        this.result = 0.0;
    }

    public MyCalculator(double initResult) {
        this.result = initResult;
    }

    public MyCalculator add(Double number) {
        this.result += number;
        return this;
    }

    public MyCalculator minus(Double number) {
        this.result -= number;
        return this;
    }

    public MyCalculator multiply(Double number) {
        this.result *= number;
        return this;
    }

    public MyCalculator divide(Double number) {
        if (number == 0.0) {
            throw new ZeroDivisionException();
        }
        this.result /= number;
        return this;
    }

    public Double getResult() {
        return this.result;
    }

    public static class ZeroDivisionException extends RuntimeException {

    }


}
