package com.example.dayonetest.repository;

import com.example.dayonetest.model.StudentFail;
import com.example.dayonetest.model.StudentPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentFailRepository extends JpaRepository<StudentFail, Long> {
}
