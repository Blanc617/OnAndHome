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

    /** 공지사항 전체 조회 */
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    /** 공지사항 단건 조회 */
    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElse(null);
    }

    /** 공지사항 저장 (등록 시 생성 시간 기록) */
    public Notice save(Notice notice) {
        notice.setCreatedAt(LocalDateTime.now());
        return noticeRepository.save(notice);
    }

    /** 공지사항 수정 (수정 시 updatedAt 자동 갱신) */
    public Notice update(Long id, Notice updated) {
        Notice existing = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다."));
        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        existing.setWriter(updated.getWriter());
        existing.setUpdatedAt(LocalDateTime.now()); // ✅ 수정 시간 자동 갱신
        return noticeRepository.save(existing);
    }

    /** 공지사항 삭제 */
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
