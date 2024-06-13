package com.shivani.quizapp.service;

import com.shivani.quizapp.dao.QuestionDao;
import com.shivani.quizapp.dao.QuizDao;
import com.shivani.quizapp.model.Question;
import com.shivani.quizapp.model.QuestionWrapper;
import com.shivani.quizapp.model.Quiz;
import com.shivani.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, String title) {
        List<Question> questions = questionDao.findByCategory(category);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionList = quiz.get().getQuestions();
        List<QuestionWrapper> questionWrapperList = new ArrayList<>();
        for(Question q : questionList){
            QuestionWrapper wrapper = new QuestionWrapper(q.getId(), q.getQuestion(), q.getOption1(), q.getOption2());
            questionWrapperList.add(wrapper);
        }

        return new ResponseEntity<>(questionWrapperList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int right = 0;
        int i = 0;
        for(Response res : response){
            if(res.getResponse().equals(questions.get(i).getAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
