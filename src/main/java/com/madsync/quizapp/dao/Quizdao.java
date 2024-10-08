package com.madsync.quizapp.dao;

import com.madsync.quizapp.modal.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Quizdao extends JpaRepository<Quiz, Integer> {
}
