package com.onandhome.Notice;


import com.onandhome.Notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }

    public Notice save(Notice notice) {
        notice.setCreatedAt(LocalDateTime.now());
        return noticeRepository.save(notice);
    }

    public Notice update(Long id, Notice updated) {
        Notice existing = noticeRepository.findById(id).orElseThrow();
        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        existing.setWriter(updated.getWriter());
        return noticeRepository.save(existing);
    }

    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
