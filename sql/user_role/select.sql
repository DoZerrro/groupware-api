## 사용자권한설정 - 권한그룹기준/사용자기준 - 권한그룹 조회 조건 중 회사명 목록 조회
select company_no, company_name
from tbl_company
where company_no = ${companyNo}
;

## 사용자권한설정 - 권한그룹기준 - 권한그룹 조회
# @param(companyNo) : 로그인한 계정의 회사코드 값
# @param(search) : 권한그룹명 검색어 입력값
select role_group_no, role_group_name, company_no
from tbl_role_group
where role_group_use = true
and company_no = ${companyNo}
and role_group_name like concat('%', ${roleGroupSearch}, '%')
;


## 사용자권한설정 - 권한그룹기준 - 선택된 권한그룹이 부여된 조직 목록 조회
# @param(companyNo) : 로그인한 계정의 회사코드 값
# @param(roleGroupNo) : 선택한 권한그룹 코드 값
# @param(userListSearch
##> 선택한 권한그룹과 검색어(부서/[직급]/이름/ID)를 기준으로 권한이 부여된 사용자를 조회한다
##    >> 회사 코드와 권한그룹코드를 넘겨줄 수 있다
##    >> 조건으로는 해당 권한그룹이 부여된 조직(orga) 정보를 출력한다.
##    >> 조직정보(회사/부서 까지의 계층정보), 직급, 사원명(ID)출력

# 로그인한 사용자의 회사 정보 기준으로 동작하는 것이 전제
# 선택한 권한그룹이 부여된 모든 조직(회사/부서/사원/) 목록 출력
# 선택한 권한그룹이 부여된 orga를 찾아서 emp를 연결하는게 좋은가?
# 조직정보, 직급, 이름, ID 필요함


SELECT FN_GET_HIERARCHY_ORGA(case when rg.orga_type = 'emp' then rg.upper_orga_no else rg.orga_no end) orga_info
     , tem.emp_rank
     , te.emp_name
     , te.username
     , rg.orga_no
FROM (
         SELECT trg.role_group_no
              , trg.role_group_name
              , trg.company_no
              , tor.orga_role_no
              , tor.orga_no
              , to2.upper_orga_no
              , to2.orga_type
              , (SELECT tc.company_name FROM tbl_company tc WHERE tc.company_no = trg.company_no) as company_name
         FROM tbl_role_group trg
                  LEFT JOIN tbl_orga_role tor ON tor.role_group_no = trg.role_group_no
                  LEFT JOIN tbl_orga to2 ON to2.orga_no = tor.orga_no
         WHERE trg.role_group_use = true
           AND trg.role_group_no = ${roleGroupNo}
           AND trg.company_no = ${companyNo}
     ) rg
         JOIN tbl_orga og ON og.orga_no = rg.orga_no
         LEFT JOIN tbl_dept_mapping tdm ON tdm.orga_no = og.orga_no
         LEFT JOIN tbl_dept td ON tdm.dept_no = td.dept_no
         LEFT JOIN tbl_emp_mapping tem ON tem.orga_no = og.orga_no
         LEFT JOIN tbl_emp te ON te.emp_no = tem.emp_no
# <if test="uerListSearch != '' and">
WHERE (
          rg.company_name LIKE CONCAT('%', ${userListSearch}, '%')
       OR td.dept_name LIKE CONCAT('%', ${userListSearch}, '%')
       OR tem.emp_rank LIKE CONCAT('%', ${userListSearch}, '%')
       OR te.emp_name  LIKE CONCAT('%', ${userListSearch}, '%')
       OR te.username  LIKE CONCAT('%', ${userListSearch}, '%')
    )
# </if>
;



## 공통 조직도 팝업에서 선택한 사용자 확인 누르면 자동으로 부모 쪽에서 선택하고 있는 권한 그룹 부여
# 권한그룹이 선택되지 않았을 경우 '공통 조직도 팝업'을 띄우지 않아야 한다.
# 공통 조직도 팝업에서 선택한 사용자의 orgaNo를 배열로 받아와서 처리
#   > 이미 해당 권한이 부여된 사원이 있는 경우 처리하는 방법
#       1) 이미 권한이 부여되어있다고 메시지 출력하고 처리 안하는 방법
#       2) 이미 권한이 부여된 사원을 제외하고 나머지 인원만 부여하는 방법

select te.emp_name, te.username, tog.orga_no, tog.upper_orga_no
from tbl_emp te
left join tbl_emp_mapping tem on te.emp_no = tem.emp_no
left join tbl_orga tog on tem.orga_no = tog.orga_no
;

with recursive cte as (
    select orga_no
         , upper_orga_no
         , orga_name
         , orga_type
    from temp_orga
    where orga_no = ${orgaNo}
    union all
    select tog.orga_no
         , tog.upper_orga_no
         , tog.orga_name
         , tog.orga_type
    from cte
    join temp_orga tog on tog.upper_orga_no = cte.orga_no
),
temp_orga as (
    select o.orga_no
         , case when o.orga_type = 'company' then (select c.company_no from tbl_company c where c.orga_no = o.orga_no)
                when o.orga_type = 'dept' then (select d.dept_no from tbl_dept d join tbl_dept_mapping dM on d.dept_no = dM.dept_no where dM.orga_no = o.orga_no)
                when o.orga_type = 'emp' then (select e.emp_no from tbl_emp e join tbl_emp_mapping eM on e.emp_no = eM.emp_no where eM.orga_no = o.orga_no)
                else 0 end orga_code
         , case when o.orga_type = 'company' then (select c.company_name from tbl_company c where c.orga_no = o.orga_no)
                when o.orga_type = 'dept' then (select d.dept_name from tbl_dept d join tbl_dept_mapping dM on d.dept_no = dM.dept_no where dM.orga_no = o.orga_no)
                when o.orga_type = 'emp' then (select e.emp_name from tbl_emp e join tbl_emp_mapping eM on e.emp_no = eM.emp_no where eM.orga_no = o.orga_no)
                else 0 end orga_name
         , o.upper_orga_no
         , o.orga_type
    from tbl_orga o
)
select *
from cte
;

select *
from tbl_orga_role
;

## 선택된 조직(회사/부서/사원)의 조직번호(orga_no) 를 받아와서 이미 해당 권한그룹이 부여된 조직을 제외하고 권한 부여
# 소속된 회사번호(company_no)와 선택한 권한그룹의 번호(role_group_no) 를 기준으로
# 선택한 사용자중 이미 해당 권 \ 한그룹이 부여된 사람은 제외하고 권한그룹을 부여해야 한다 -> orga_no 가 담긴 List에서 not in 조건으로 처리
# @param()
insert into tbl_orga_role (role_group_no, orga_no, reg_user)
select ${roleGroupNo}, t.orga_no, '준성'
from tbl_emp te
left join tbl_emp_mapping tem on te.emp_no = tem.emp_no
left join tbl_orga t on tem.orga_no = t.orga_no
where t.orga_no not in (select tog.orga_no
                        from tbl_orga_role tor
                        left join tbl_orga tog on tor.orga_no = tog.orga_no
                        where role_group_no = ${roleGroupNo})
and t.orga_no in (${orgaNoList})
;


## 조직에 부여된 권한
select *
from tbl_orga_role tor
where tor.orga_no in (19, 20)
and tor.role_group_no = 4
;

delete
from tbl_orga_role
where orga_no in (19, 20)
and role_group_no = 4
;

## 사용자권한설정 - 사용자기준 - 선택된 회사에 소속된 사용자 목록 조회
# 회사코드 기준으로 조직에서 사용자 정보 조회
# 회사이름, 부서이름, [직급], 이름(ID) 그리드에 출력
# @param() : $
