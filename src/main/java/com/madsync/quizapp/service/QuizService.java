package com.madsync.quizapp.service;

import com.madsync.quizapp.dao.QuestionDao;
import com.madsync.quizapp.dao.Quizdao;
import com.madsync.quizapp.modal.Question;
import com.madsync.quizapp.modal.QuestionWrapper;
import com.madsync.quizapp.modal.Quiz;
import com.madsync.quizapp.modal.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    Quizdao quizdao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

       List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizdao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizdao.findById(id);
        List<Question> questionsFromDB =  quiz.get().getQuestions();
        List<QuestionWrapper> questionForUsers = new ArrayList<>();
        for (Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUsers.add(qw);
        }
        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizdao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int rightAnswers = 0;

        for (int i = 0; i < responses.size(); i++) {
            if(responses.get(i).getResponse().equals(questions.get(i).getRightAnswer())){
                rightAnswers++;
            }
        }
        return new ResponseEntity<>(rightAnswers,HttpStatus.OK);
    }
}
