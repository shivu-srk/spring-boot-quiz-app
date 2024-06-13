package com.shivani.quizapp.dao;

import com.shivani.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
    Optional<Quiz> findAllById(Integer id);
}
