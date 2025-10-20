
document.addEventListener('DOMContentLoaded', () => {
  const root = document.querySelector('.on-side-menu-nav .side-menu-list');
  if (!root) return;

  // 유틸: 부드럽게 열기/닫기
  const openSub = (li, sub) => {
    li.classList.add('is-open');
    // 먼저 auto 높이를 얻기 위해 일시적으로 펼쳐 측정
    sub.style.display = 'block';
    const target = sub.scrollHeight + 'px';
    sub.style.maxHeight = target;
    // 전환 끝나면 max-height 초기화(콘텐츠 높이 변화 대응)
    sub.addEventListener('transitionend', function tidy(e){
      if (e.propertyName === 'max-height') sub.style.maxHeight = sub.scrollHeight + 'px';
      sub.removeEventListener('transitionend', tidy);
    });
  };

  const closeSub = (li, sub) => {
    // 현재 높이를 기준으로 0으로 전환
    sub.style.maxHeight = sub.scrollHeight + 'px';
    requestAnimationFrame(() => {
      li.classList.remove('is-open');
      sub.style.maxHeight = '0px';
      sub.addEventListener('transitionend', function tidy(e){
        if (e.propertyName === 'max-height') sub.style.display = 'none';
        sub.removeEventListener('transitionend', tidy);
      });
    });
  };

  // 모든 1뎁스 li 설정
  root.querySelectorAll(':scope > li').forEach((li) => {
    const a = li.querySelector(':scope > a');
    const sub = li.querySelector(':scope > ul');
    if (!a || !sub) return; // 2뎁스 없는 항목은 건너뛰기

    // 초기 숨김
    sub.style.display = 'none';
    sub.style.maxHeight = '0px';

    a.addEventListener('click', (e) => {
      // 토글만 하고 싶으면 기본 이동 막기
      e.preventDefault();

      const isOpen = li.classList.contains('is-open');

      // 하나만 열리게 하려면: 다른 것들 닫기
      root.querySelectorAll(':scope > li.is-open').forEach((opened) => {
        if (opened === li) return;
        const openedSub = opened.querySelector(':scope > ul');
        if (openedSub) closeSub(opened, openedSub);
      });

      // 클릭한 것 토글
      isOpen ? closeSub(li, sub) : openSub(li, sub);
    });

    // 옵션: 열려있는 상태에서 Alt+클릭 시 실제 링크로 이동하는 동작을 원하면 아래 사용
    // a.addEventListener('click', (e) => {
    //   if (li.classList.contains('is-open') && e.altKey) {
    //     window.location.href = a.getAttribute('href') || '#';
    //   }
    // });
  });
});

