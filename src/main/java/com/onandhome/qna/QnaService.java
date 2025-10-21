package com.onandhome.qna;


import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    public List<Qna> findAll() {
        return qnaRepository.findAll();
    }

    public Qna findById(Long id) {
        return qnaRepository.findById(id).orElse(null);
    }

    public Qna save(Qna qna) {
        qna.setCreatedAt(LocalDateTime.now());
        return qnaRepository.save(qna);
    }

    public Qna update(Long id, Qna updated) {
        Qna qna = qnaRepository.findById(id).orElseThrow();
        qna.setTitle(updated.getTitle());
        qna.setWriter(updated.getWriter());
        qna.setQuestion(updated.getQuestion());
        qna.setAnswer(updated.getAnswer());
        return qnaRepository.save(qna);
    }

    public void delete(Long id) {
        qnaRepository.deleteById(id);
    }
}
