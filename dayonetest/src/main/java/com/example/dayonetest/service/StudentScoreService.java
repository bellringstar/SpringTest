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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;

    public void saveScore(String studentName, String exam, Integer korScore, Integer englishScore, Integer mathScore) {
        StudentScore studentScore = StudentScore.builder()
                .exam(exam)
                .studentName(studentName)
                .korScore(korScore)
                .englishName(englishScore)
                .mathScore(mathScore)
                .build();
        studentScoreRepository.save(studentScore);

        MyCalculator myCalculator = new MyCalculator(0.0);
        Double avgScore = myCalculator
                .add(korScore.doubleValue())
                .add(englishScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if (avgScore >= 60) {
            studentPassRepository.save(
                    StudentPass.builder()
                            .exam(exam)
                            .studentName(studentName)
                            .avgScore(avgScore)
                            .build()
            );
        } else {
            studentFailRepository.save(
                    StudentFail.builder()
                            .exam(exam)
                            .studentName(studentName)
                            .avgScore(avgScore)
                            .build()
            );
        }
    }

    public List<ExamPassStudentResponse> getPassStudentList(String exam) {
        List<StudentPass> studentPasses = studentPassRepository.findAll();

        return studentPasses.stream()
                .filter(pass -> pass.getExam().equals(exam))
                .map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
    }

    public List<ExamFailStudentResponse> getFailStudentList(String exam) {
        List<StudentFail> studentFails = studentFailRepository.findAll();

        return studentFails.stream()
                .filter(pass -> pass.getExam().equals(exam))
                .map(pass -> new ExamFailStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
    }

}
