package com.example.dayonetest.model;

import com.example.dayonetest.calculator.MyCalculator;

public class StudentFailFixture {

    public static StudentFail create(StudentScore studentScore) {
        var calc = new MyCalculator();
        return StudentFail
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

    public static StudentFail create(String studentName, String exam) {
        return StudentFail
                .builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(40.0)
                .build();
    }
}
