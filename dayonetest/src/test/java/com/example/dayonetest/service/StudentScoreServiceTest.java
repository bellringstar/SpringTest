package com.example.dayonetest.service;

import com.example.dayonetest.calculator.MyCalculator;
import com.example.dayonetest.controller.response.ExamFailStudentResponse;
import com.example.dayonetest.controller.response.ExamPassStudentResponse;
import com.example.dayonetest.model.StudentFail;
import com.example.dayonetest.model.StudentPass;
import com.example.dayonetest.model.StudentScore;
import com.example.dayonetest.repository.StudentFailRepository;
import com.example.dayonetest.repository.StudentPassRepository;
import com.example.dayonetest.repository.StudentScoreRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

    @Test
    @DisplayName("성적 저장 애플리케이션 / 60점 이상인 경우")
    public void saveScoreMockTest() {
        //given : 평균 점수가 60점 이상인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "test1";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEngScore = 100;
        Integer givenMathScore = 60;

        StudentScore expectStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishName(givenEngScore)
                .mathScore(givenMathScore)
                .build();
        StudentPass expectStudentPass = StudentPass.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        (new MyCalculator(0.0))
                                .add(givenKorScore.doubleValue())
                                .add(givenEngScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                ).build();
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPssArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        //when

        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEngScore,
                givenMathScore
        );

        //then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());
        Assertions.assertEquals(expectStudentScore.getEnglishName(), capturedStudentScore.getEnglishName());
        Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPssArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPssArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentPass.getStudentName(), capturedStudentPass.getStudentName());
        Assertions.assertEquals(expectStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());
        Assertions.assertEquals(expectStudentPass.getExam(), capturedStudentPass.getExam());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 애플리케이션 / 60점 미만인 경우")
    public void saveScoreMockTest2() {
        //given : 평균 점수가 60점 이상인 경우
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "test1";
        String givenExam = "testExam";
        Integer givenKorScore = 10;
        Integer givenEngScore = 20;
        Integer givenMathScore = 30;

        StudentScore expectStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishName(givenEngScore)
                .mathScore(givenMathScore)
                .build();
        StudentFail expectStudentFail = StudentFail.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        (new MyCalculator(0.0))
                                .add(givenKorScore.doubleValue())
                                .add(givenEngScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                ).build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        //when

        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEngScore,
                givenMathScore
        );

        //then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());
        Assertions.assertEquals(expectStudentScore.getEnglishName(), capturedStudentScore.getEnglishName());
        Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        Assertions.assertEquals(expectStudentFail.getStudentName(), capturedStudentFail.getStudentName());
        Assertions.assertEquals(expectStudentFail.getAvgScore(), capturedStudentFail.getAvgScore());
        Assertions.assertEquals(expectStudentFail.getExam(), capturedStudentFail.getExam());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentListTest() {
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentPass expectStudent1 = StudentPass.builder()
                .id(1L)
                .studentName("testName1")
                .exam("testexam")
                .avgScore(80.0)
                .build();
        StudentPass expectStudent2 = StudentPass.builder()
                .id(2L)
                .studentName("testName2")
                .exam("testexam")
                .avgScore(70.0)
                .build();
        StudentPass notExpectedStudent = StudentPass.builder()
                .id(3L)
                .studentName("testName3")
                .exam("secondexam")
                .avgScore(80.0).build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectedStudent
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
        String givenTestExam = "testexam";

        // when
        var expectResponses = List.of(expectStudent1, expectStudent2)
                .stream()
                .map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();

        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentList(givenTestExam);

        // then
        Assertions.assertIterableEquals(expectResponses, responses);
    }

    @Test
    @DisplayName("60점 미만 리스트")
    public void getFailStudentListTest() {
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentFail expectStudent1 = StudentFail.builder()
                .id(1L)
                .studentName("testName1")
                .exam("testexam")
                .avgScore(50.0)
                .build();
        StudentFail expectStudent2 = StudentFail.builder()
                .id(2L)
                .studentName("testName2")
                .exam("testexam")
                .avgScore(40.0)
                .build();
        StudentFail notExpectedStudent = StudentFail.builder()
                .id(3L)
                .studentName("testName3")
                .exam("second")
                .avgScore(90.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectStudent1,
                expectStudent2,
                notExpectedStudent
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        // when
        var expectResponses = List.of(expectStudent1, expectStudent2)
                .stream()
                .map(pass -> new ExamFailStudentResponse(pass.getStudentName(), pass.getAvgScore())).toList();

        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentList("testexam");

        // then
        Assertions.assertIterableEquals(expectResponses, responses);

    }

}