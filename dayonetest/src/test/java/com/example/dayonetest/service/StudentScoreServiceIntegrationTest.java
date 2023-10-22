package com.example.dayonetest.service;

import com.example.dayonetest.IntegrationTest;
import com.example.dayonetest.calculator.MyCalculator;
import com.example.dayonetest.controller.response.ExamFailStudentResponse;
import com.example.dayonetest.controller.response.ExamPassStudentResponse;
import com.example.dayonetest.model.StudentScoreFixture;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentScoreServiceIntegrationTest extends IntegrationTest {
    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void savePassedStudentScoreTest() {
        // given
        var studentScore = StudentScoreFixture.passed();

        // whne
        studentScoreService.saveScore(
                studentScore.getStudentName(),
                studentScore.getExam(),
                studentScore.getKorScore(),
                studentScore.getEnglishName(),
                studentScore.getMathScore()
        );
        entityManager.flush();
        entityManager.clear();

        // then
        List<ExamPassStudentResponse> passStudentList = studentScoreService.getPassStudentList(studentScore.getExam());

        Assertions.assertEquals(1, passStudentList.size());

        var passedStudentResponse = passStudentList.get(0);

        var calc = new MyCalculator(0);
        var avgScore = calc.add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .add(studentScore.getEnglishName().doubleValue())
                .divide(3.0)
                .getResult();
        Assertions.assertEquals(studentScore.getStudentName(), passedStudentResponse.getStudentName());
        Assertions.assertEquals(avgScore, passedStudentResponse.getAvgScore());
    }

    @Test
    public void saveFailedStudentScoreTest() {
        // given
        var studentScore = StudentScoreFixture.failed();

        // whne
        studentScoreService.saveScore(
                studentScore.getStudentName(),
                studentScore.getExam(),
                studentScore.getKorScore(),
                studentScore.getEnglishName(),
                studentScore.getMathScore()
        );
        entityManager.flush();
        entityManager.clear();

        // then
        List<ExamFailStudentResponse> failStudentList = studentScoreService.getFailStudentList(studentScore.getExam());

        Assertions.assertEquals(1, failStudentList.size());

        var failedStudentResponse = failStudentList.get(0);

        var calc = new MyCalculator(0);
        var avgScore = calc.add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .add(studentScore.getEnglishName().doubleValue())
                .divide(3.0)
                .getResult();
        Assertions.assertEquals(studentScore.getStudentName(), failedStudentResponse.getStudentName());
        Assertions.assertEquals(avgScore, failedStudentResponse.getAvgScore());
    }
}
