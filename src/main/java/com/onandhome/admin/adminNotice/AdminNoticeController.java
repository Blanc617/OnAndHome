package com.onandhome.admin.adminNotice;

import com.onandhome.Notice.NoticeService;
import com.onandhome.Notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 공지사항 관리 컨트롤러 (MVC 버전)
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    /** 공지사항 목록 */
    @GetMapping("/list")
    public String list(Model model) {
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "admin/board/notice/list";
    }

    /** 공지사항 상세 보기 */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "admin/board/notice/detail";
    }

    /** 공지 작성 폼 */
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "admin/board/notice/write";
    }

    /** 공지 저장 */
    @PostMapping("/write")
    public String write(@ModelAttribute Notice notice) {
        noticeService.save(notice);
        return "redirect:/admin/board/notice/list";
    }

    /** 공지 수정 폼 */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "admin/board/notice/edit";
    }

    /** 공지 수정 저장 */
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Notice notice) {
        noticeService.update(id, notice);
        return "redirect:/admin/board/notice/list";
    }

    /** 공지 삭제 */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        noticeService.delete(id);
        return "redirect:/admin/board/notice/list";
    }
}
