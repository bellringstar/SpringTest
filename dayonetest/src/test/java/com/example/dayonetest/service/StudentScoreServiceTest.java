package com.example.dayonetest.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.dayonetest.repository.StudentFailRepository;
import com.example.dayonetest.repository.StudentPassRepository;
import com.example.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StudentScoreServiceTest {

    @Test
    @DisplayName("첫 번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );
        String givenStudentName = "test1";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEngScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEngScore,
                givenMathScore);

    }

}