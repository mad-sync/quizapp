package com.madsync.quizapp.service;

import com.madsync.quizapp.dao.QuestionDao;
import com.madsync.quizapp.modal.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestion(){
        return questionDao.findAll();
    }

    public List<Question> getQuestionByCategory(String category){
        return questionDao.findByCategory(category);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        Question question = questionDao.findById(id).orElse( new Question());
        questionDao.delete(question);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
