package com.onandhome.Notice;

import com.onandhome.Notice.dto.NoticeDto;
import com.onandhome.Notice.entity.Notice;
import com.onandhome.Notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /** ✅ 전체 조회 (DTO 변환 포함) */
    public List<NoticeDto> findAll() {
        return noticeRepository.findAll()
                .stream()
                .map(NoticeDto::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ 단일 조회 */
    public NoticeDto findById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));
        return NoticeDto.fromEntity(notice);
    }

    /** ✅ 새 공지 등록 (Controller → 여기로 호출됨) */
    public void createNotice(NoticeDto dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수 입력 항목입니다.");
        }

        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setWriter(dto.getWriter());
        notice.setContent(dto.getContent());
        notice.setCreatedAt(LocalDateTime.now());
        notice.setUpdatedAt(null);

        noticeRepository.save(notice);
    }

    /** ✅ 수정 */
    public void update(Long id, NoticeDto dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));
        notice.setTitle(dto.getTitle());
        notice.setWriter(dto.getWriter());
        notice.setContent(dto.getContent());
        notice.setUpdatedAt(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    /** ✅ 삭제 */
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
