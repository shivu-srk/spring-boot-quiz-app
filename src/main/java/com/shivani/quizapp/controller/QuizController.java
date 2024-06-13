package com.shivani.quizapp.controller;

import com.shivani.quizapp.model.Question;
import com.shivani.quizapp.model.QuestionWrapper;
import com.shivani.quizapp.model.Response;
import com.shivani.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("")
    public ResponseEntity<String> create(@RequestParam String category, @RequestParam String title) {
        return quizService.createQuiz(category, title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submit(@PathVariable Integer id, @RequestBody List<Response> response){
        return quizService.calculateResult(id, response);
    }
}
