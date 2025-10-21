package com.onandhome.admin.adminNotice;

import com.onandhome.Notice.NoticeService;
import com.onandhome.Notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    /** 목록 */
    @GetMapping("/list")
    public String list(Model model) {
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "admin/board/notice/list";
    }

    /** 상세 보기 */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "admin/board/notice/detail";
    }

//    @PostMapping("/write")
//    public String write(@ModelAttribute Notice notice, @AuthenticationPrincipal User user) {
//        if (user == null) {
//            // 로그인 안 한 상태로 접근 → 거부
//            return "redirect:/user/login";
//        }
//        notice.setWriter(user.getUsername());
//        noticeService.save(notice);
//        return "redirect:/admin/board/notice/list";
//    }


    /** 작성 화면 */
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "admin/board/notice/write";
    }

    /** 작성 저장 */
    @PostMapping("/write")
    public String write(@ModelAttribute Notice notice) {
        noticeService.save(notice);
        return "redirect:/admin/board/notice/list";
    }

    /** 수정 화면 */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "admin/board/notice/edit";
    }

    /** 수정 저장 */
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Notice notice) {
        noticeService.update(id, notice);
        return "redirect:/admin/board/notice/list";
    }

    /** 삭제 */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        noticeService.delete(id);
        return "redirect:/admin/board/notice/list";
    }


//    @PostMapping("/edit/{id}")
//    public String edit(@PathVariable Long id, @ModelAttribute Notice notice, @AuthenticationPrincipal User user) {
//        if (user == null) return "redirect:/user/login";
//        notice.setWriter(user.getUsername());
//        noticeService.update(id, notice);
//        return "redirect:/admin/board/notice/list";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
//        if (user == null) return "redirect:/user/login";
//        noticeService.delete(id);
//        return "redirect:/admin/board/notice/list";
//    }

}