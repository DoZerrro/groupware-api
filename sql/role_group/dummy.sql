# error code 1366
alter table role_group
collate = 'utf8mb4_general_ci',
    convert to charset utf8mb4;


## 권한그룹을 추가할때 조직권한 테이블에 어떤조직에 대한 권한인지 같이 추가해주어야한다. (트랜잭션?, 트리거?)
# 권한그룹
# TODO : 회사명 vaildation
insert into role_group (role_group_name, role_group_use, company_name)
values ('카카오 기본사원 권한', true, 'KAKAO');