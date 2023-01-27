########################################################################################################################
#tbl_menu insert

INSERT INTO tbl_menu (upper_menu_no, menu_name, menu_code, menu_icon, reg_user)
VALUES (null, '시스템 설정', 'SYS', '#', 'admin'),
       (1, '조직관리', 'ORGA', '#', 'admin'),
       (2, '회사관리', 'ORGA0010', '#', 'admin'),
       (2, '부서관리', 'ORGA0020', '#', 'admin'),
       (2, '사원관리', 'ORGA0030', '#', 'admin'),
       (1, '권한관리', 'ROLE', '#', 'admin'),
       (6, '권한그룹설정', 'ROLE0010', '#', 'admin'),
       (6, '사용자권한설정', 'ROLE0020', '#', 'admin'),
       (6, '메뉴사용설정', 'ROLE0030', '#', 'admin'),
       (null, '메일', 'MAIL', '#', 'admin'),
       (null, '게시판', 'BOARD', '#', 'admin'),
       (null, '드라이브', 'DRIVE', '#', 'admin'),
       (null, '일정', 'CALENDER', '#', 'admin');

########################################################################################################################
########################################################################################################################
#tbl_role_group insert

insert into tbl_role_group (role_group_name, company_no, reg_user)
values ('더존 기본권한', 1, 'admin');
insert into tbl_role_group (role_group_name, company_no, reg_user)
values ('더존 개발팀 권한', 1, 'admin');
insert into tbl_role_group (role_group_name, company_no, reg_user)
values ('더존 개발3-1팀 권한', 1, 'admin');
insert into tbl_role_group (role_group_name, company_no, reg_user)
values ('더존 담당자 권한', 1, 'admin');
insert into tbl_role_group (role_group_name, company_no, reg_user)
values ('CJ 개발팀 권한', 4, 'admin');

########################################################################################################################
########################################################################################################################
#tbl_menu_role insert

##더존의 기본권한(메일, 게시판, 드라이브, 일정)
insert into tbl_menu_role (role_group_no, menu_no, reg_user)
VALUES (1, 10, 'admin'),
       (1, 11, 'admin'),
       (1, 12, 'admin'),
       (1, 13, 'admin');


##더존의 개발팀 권한(시스템설정, 권한관리, 권한그룹설정, 사용자권한설정, 메뉴사용설정)
insert into tbl_menu_role (role_group_no, menu_no, reg_user)
VALUES (2, 1, 'admin'),
       (2, 6, 'admin'),
       (2, 7, 'admin'),
       (2, 8, 'admin'),
       (2, 9, 'admin');

##더존의 개발 3-1팀 권한(시스템설정, 조직관리, 사원관리)
insert into tbl_menu_role (role_group_no, menu_no, reg_user)
VALUES (3, 1, 'admin'),
       (3, 2, 'admin'),
       (3, 5, 'admin')
;

##더존의 담당자 권한(시스템설정, 조직관리, 회사관리, 부서관리, 사원관리)
insert into tbl_menu_role (role_group_no, menu_no, reg_user)
VALUES (4, 1, 'admin'),
       (4, 2, 'admin'),
       (4, 3, 'admin'),
       (4, 4, 'admin'),
       (4, 5, 'admin');

##CJ의 개발팀 권한(시스템설정, 권한관리, 권한그룹설정, 사용자권한설정, 메뉴사용설정)
insert into tbl_menu_role (role_group_no, menu_no, reg_user)
VALUES (5, 1, 'admin'),
       (5, 6, 'admin'),
       (5, 7, 'admin'),
       (5, 8, 'admin'),
       (5, 9, 'admin');
