# 메뉴 생성
INSERT INTO menu (upper_menu_no, menu_name, menu_code)
VALUES (null, '시스템 설정', 'SYS'),
       (1, '조직관리', 'ORGA'),
       (2, '회사관리', 'ORGA0010'),
       (2, '부서관리', 'ORGA0020'),
       (2, '사원관리', 'ORGA0030'),
       (1, '권한관리', 'ROLE'),
       (6, '권한그룹설정', 'ROLE0010'),
       (6, '사용자권한설정', 'ROLE0020'),
       (6, '메뉴사용설정', 'ROLE0030'),
       (null, '메일', 'MAIL'),
       (null, '게시판', 'BOARD'),
       (null, '드라이브', 'DRIVE'),
       (null, '일정', 'CALENDER');
