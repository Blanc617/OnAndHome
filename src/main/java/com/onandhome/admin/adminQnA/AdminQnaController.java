package com.onandhome.admin.adminQnA;

import com.onandhome.qna.QnaService;
import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Q&A 관리 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/qna")
public class AdminQnaController {

    private final QnaService qnaService;

    /** QnA 목록 페이지 */
    @GetMapping("/list")
    public String list(Model model) {
        List<Qna> qnaList = qnaService.findAll();
        model.addAttribute("qnaList", qnaList);
        return "admin/board/qna/list";
    }

    /** QnA 작성 폼 */
    @GetMapping("/write")
    public String writeView(Model model) {
        model.addAttribute("qna", new Qna());
        return "admin/board/qna/write";
    }

    /** QnA 작성 저장 */
    @PostMapping("/write")
    public String write(@ModelAttribute Qna qna) {
        qnaService.save(qna);
        return "redirect:/admin/board/qna/list";
    }

    /** QnA 상세 보기 */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Qna qna = qnaService.findById(id);
        model.addAttribute("qna", qna);
        return "admin/board/qna/detail";
    }

    /** QnA 수정 폼 */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Qna qna = qnaService.findById(id);
        model.addAttribute("qna", qna);
        return "admin/board/qna/edit";
    }

    /** QnA 수정 저장 */
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Qna qna) {
        qnaService.update(id, qna);
        return "redirect:/admin/board/qna/list";
    }

    /** QnA 삭제 */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        qnaService.delete(id);
        return "redirect:/admin/board/qna/list";
    }

    // 답변 등록
    @PostMapping("/answer/{id}")
    public String addAnswer(@PathVariable Long id,
                            @RequestParam("answer") String answer) {
        Qna qna = qnaService.findById(id);
        if (qna == null) throw new IllegalArgumentException("Q&A 없음");
        qna.setAnswer(answer);
        qnaService.save(qna);
        return "redirect:/admin/board/qna/" + id;
    }

    // 수정 페이지 이동
    @GetMapping("/answer/edit/{id}")
    public String editAnswerForm(@PathVariable Long id, Model model) {
        Qna qna = qnaService.findById(id);
        if (qna == null) throw new IllegalArgumentException("Q&A 없음");
        model.addAttribute("qna", qna);
        return "admin/board/qna/edit"; // edit.html로 이동
    }

    // 답변 수정 (저장)
    @PostMapping("/answer/edit/{id}")
    public String editAnswer(@PathVariable Long id,
                             @RequestParam("answer") String answer) {
        Qna qna = qnaService.findById(id);
        if (qna == null) throw new IllegalArgumentException("Q&A 없음");
        qna.setAnswer(answer); // (수정됨) 제거해서 깔끔하게 유지
        qnaService.save(qna);
        return "redirect:/admin/board/qna/" + id;
    }

    // 답변 삭제
    @PostMapping("/answer/delete/{id}")
    public String deleteAnswer(@PathVariable Long id) {
        Qna qna = qnaService.findById(id);
        if (qna == null) throw new IllegalArgumentException("Q&A 없음");
        qna.setAnswer(null);
        qnaService.save(qna);
        return "redirect:/admin/board/qna/" + id;
    }


}
