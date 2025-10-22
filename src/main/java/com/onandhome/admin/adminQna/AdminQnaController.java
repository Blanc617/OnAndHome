package com.onandhome.admin.adminQnA;

import com.onandhome.admin.adminQnA.entity.Answer;
import com.onandhome.qna.QnaService;
import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/qna")
public class AdminQnaController {

    private final QnaService qnaService;
    private final AnswerService answerService;

    /** ✅ QnA 목록 페이지 */
    @GetMapping("/list")
    public String list(Model model) {
        List<Qna> qnaList = qnaService.findAll();
        model.addAttribute("qnaList", qnaList);
        return "admin/board/qna/list";
    }

    /** ✅ QnA 작성 폼 */
    @GetMapping("/write")
    public String writeView(Model model) {
        model.addAttribute("qna", new Qna());
        return "admin/board/qna/write";
    }

    /** ✅ QnA 작성 저장 */
    @PostMapping("/write")
    public String write(@ModelAttribute Qna qna) {
        qnaService.save(qna);
        return "redirect:/admin/board/qna/list";
    }

    /** ✅ QnA 상세 보기 */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Qna qna = qnaService.findById(id);
        if (qna == null) throw new IllegalArgumentException("Q&A 없음");

        // ✅ 이 질문에 달린 모든 답변 조회
        List<Answer> answers = answerService.findByQnaId(id);

        model.addAttribute("qna", qna);
        model.addAttribute("answers", answers);
        return "admin/board/qna/detail";
    }

    /** ✅ QnA 수정 폼 */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Qna qna = qnaService.findById(id);
        model.addAttribute("qna", qna);
        return "admin/board/qna/edit";
    }

    /** ✅ QnA 수정 저장 */
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Qna qna) {
        qnaService.update(id, qna);
        return "redirect:/admin/board/qna/list";
    }

    /** ✅ QnA 삭제 */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        qnaService.delete(id);
        return "redirect:/admin/board/qna/list";
    }

    /** ✅ 답변 등록 */
    @PostMapping("/answer/{id}")
    public String addAnswer(@PathVariable Long id,
                            @RequestParam("content") String content) {
        answerService.createAnswer(id, content, "관리자");
        return "redirect:/admin/board/qna/" + id;
    }

    /** ✅ 답변 수정 폼 */
    @GetMapping("/answer/edit/{answerId}")
    public String editAnswerForm(@PathVariable Long answerId, Model model) {
        Answer answer = answerService.findById(answerId);
        if (answer == null) throw new IllegalArgumentException("답변 없음");

        model.addAttribute("answer", answer);
        model.addAttribute("qnaId", answer.getQna().getId());
        return "admin/board/qna/answer-edit";  // ✅ 경로 수정됨 (templates/admin/board/qna/answer-edit.html)
    }

    /** ✅ 답변 수정 저장 */
    @PostMapping("/answer/edit/{answerId}")
    public String editAnswer(@PathVariable Long answerId,
                             @RequestParam("content") String content) {
        answerService.updateAnswer(answerId, content);
        Long qnaId = answerService.findById(answerId).getQna().getId();
        return "redirect:/admin/board/qna/" + qnaId;
    }

    /** ✅ 답변 삭제 */
    @PostMapping("/answer/delete/{answerId}")
    public String deleteAnswer(@PathVariable Long answerId,
                               @RequestParam("qnaId") Long qnaId) {
        answerService.deleteAnswer(answerId);
        return "redirect:/admin/board/qna/" + qnaId;
    }
}
