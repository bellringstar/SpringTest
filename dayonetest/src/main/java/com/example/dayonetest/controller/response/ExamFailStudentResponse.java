package com.example.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExamFailStudentResponse {
    private final String StudentName;
    private final Double avgScore;
}
