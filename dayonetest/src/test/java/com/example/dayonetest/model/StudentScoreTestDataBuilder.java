package com.example.dayonetest.model;

public class StudentScoreTestDataBuilder {
    //빌더를 리턴하기 때문에 오버라이딩이 가능 -> 자유도가 너무 높을지도?

    public static StudentScore.StudentScoreBuilder passed() {
        return StudentScore.builder()
                .korScore(80)
                .englishName(100)
                .mathScore(90)
                .studentName("defaultName")
                .exam("defaultExam");
    }

    public static StudentScore.StudentScoreBuilder failed() {
        return StudentScore.builder()
                .korScore(50)
                .englishName(40)
                .mathScore(39)
                .studentName("defaultName")
                .exam("defaultExam");
    }
}
