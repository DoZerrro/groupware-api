# 1. 회사와, 조직 추가
insert into orga (upper_orga_no)
values (null);

insert into company (orga_no, company_name, company_address, company_tel, company_use, owner_name, founding_date,
                     business_number)
values (last_insert_id(), 'DOUZONE',
        '강원도 순천시 남산면 수동리 749',
        '02-6233-3000',
        true,
        '김용우',
        '2003-06-01',
        '134-81-08473');

insert into orga (upper_orga_no)
values (null);
insert into company (orga_no, company_name, company_address, company_tel, company_use, owner_name, founding_date,
                     business_number)
values (last_insert_id(),
        'KAKAO',
        '제주특별자치도 제주시 첨단로 242',
        '1577-3321',
        false,
        '홍은택',
        '1995-02-01',
        '120-81-41578');

insert into orga (upper_orga_no)
values (null);
insert into company (orga_no, company_name, company_address, company_tel, company_use, owner_name, founding_date,
                     business_number)
values (last_insert_id(),
        'CJ',
        '서울특별시 중구 소월로2길 12',
        '02-726-8114',
        false,
        '손경식',
        '1953-08-01',
        '104-81-29553');

insert into orga (upper_orga_no)
values (null);
insert into company (orga_no, company_name, company_address, company_tel, company_use, owner_name, founding_date,
                     business_number)
values (last_insert_id(),
        'COUPANG',
        '서울특별시 송파구 송파대로 570',
        '1577-7011',
        true,
        '강한승',
        '2013-07-01',
        '120-88-03579');

insert into orga (upper_orga_no)
values (null);
insert into company (orga_no, company_name, company_address, company_tel, company_use, owner_name, founding_date,
                     business_number)
values (last_insert_id(),
        'MEGAZONE',
        '서울 강남구 논현로85길 46',
        '02-2109-2500',
        false,
        '장지황',
        '2001-03-01',
        '214-86-79342');


select *
from orga;

select *
from company;

# 부서와 조직 생성
insert into tbl_dept (company_no, dname, dept_sort, ddesc)
values (1, '개발팀', 1, 'DEV');

# 더존의 개발팀 (orga_no=6)
insert into orga (upper_orga_no)
values (1);
insert into deptmapping (dept_no, orga_no)
values (1, 6);

select *
from deptmapping;

# 사원과 조직 매핑
INSERT INTO emp ( emp_name, username, `password`, gender, emp_phone, emp_address, emp_profile, email
                , last_login_time, last_login_ip, first_hiredate, last_retiredate)
VALUES ( '정수연', 'user01', 'userpwd01', '여성', '01088873822'
       , '부산시 동래구', 'profile', 'user01@naver.com', '2022-10-10', '196.168.500.1', '2020-01-07', NULL);

# 더존의 개발팀의 정수연 사원
# orga no 6번이 개발팀이다.
insert into orga (upper_orga_no)
values (6);

# 정수연 사원은 orga 7번
insert into empmapping (orga_no, emp_no, emp_rank, hiredate, retireddate)
VALUES (7, 1, '사원', now(), null);

#######################################################
###########################메뉴#########################
#######################################################

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

select *
from menu;


# 권한그룹 생성
## 권한그룹을 추가할때 조직권한 테이블에 어떤조직에 대한 권한인지 같이 추가해주어야한다. (트랜잭션?, 트리거?)
# TODO : 회사명 vaildation
insert into role_group (role_group_name, role_group_use, company_name)
values ('더존 기본사원 권한', true, 'DOUZONE');

# 메뉴 권한 생성
# 메일, 게시판, 드라이브, 일정 메뉴권한 부여
insert into menu_role (role_group_no, menu_no)
VALUES (1,10),
       (1,11),
       (1,12),
       (1,13);

# 어떤 조직에 권한그룹을 부여할거냐
# role_group_no(권한그룹번호) 1=더존기본사원권한
# orga_no(조직번호) 1=더존
insert into orga_role (role_group_no, orga_no)
VALUES (1,6);
