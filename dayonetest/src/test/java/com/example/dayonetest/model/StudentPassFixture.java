package com.example.dayonetest.model;

import com.example.dayonetest.calculator.MyCalculator;

public class StudentPassFixture {

    public static StudentPass create(StudentScore studentScore) {
        var calc = new MyCalculator();
        return StudentPass
                .builder()
                .exam(studentScore.getExam())
                .studentName(studentScore.getStudentName())
                .avgScore(calc
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishName().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult()
                )
                .build();
    }

    public static StudentPass create(String studentName, String exam) {
        return StudentPass
                .builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(80.0)
                .build();
    }
}
