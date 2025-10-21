package com.onandhome.admin.adminQna;


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

//    @PostMapping("/write")
//    public String write(@ModelAttribute Qna qna, @AuthenticationPrincipal User user) {
//        if (user == null) {
//            // 로그인 안 한 상태로 접근 → 거부
//            return "redirect:/user/login";
//        }
//        qna.setWriter(user.getUsername());
//        qnaService.save(qna);
//        return "redirect:/admin/board/notice/list";
//    }

//    @PostMapping("/edit/{id}")
//    public String edit(@PathVariable Long id, @ModelAttribute Qna qna, @AuthenticationPrincipal User user) {
//        if (user == null) return "redirect:/user/login";
//        qna.setWriter(user.getUsername());
//        qnaService.update(id, qna);
//        return "redirect:/admin/board/qna/list";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
//        if (user == null) return "redirect:/user/login";
//        qnaService.delete(id);
//        return "redirect:/admin/board/qna/list";
//    }

}
