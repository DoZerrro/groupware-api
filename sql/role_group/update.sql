# 권한그룹번호로 권한그룹명 변경하기
update role_group
set role_group_name = '바꿀권한그룹명'
where role_group_no = 1;

# 권한그룹번호로 권한그룹 사용여부 변경하기
update role_group
set role_group_use = true
where role_group_no = 1;

