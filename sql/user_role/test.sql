# 회사
select * from tbl_company;
# 부서
select * from tbl_dept;
# 부서 맵핑
select * from tbl_dept_mapping;
# 사원
select * from tbl_emp;
# 사원 맵핑
select * from tbl_emp_mapping;
# 조직
select * from tbl_orga;
# 메뉴
select * from tbl_menu;
# 권한 그룹
select * from tbl_role_group;
# 메뉴 맵핑
select * from tbl_menu_role;
# 조직 권한
select * from tbl_orga_role;

###########################################################################################

select * from tbl_menu;

## memu 계층구조 조회 쿼리
WITH RECURSIVE CTE AS (
    SELECT
        menu_no,
        menu_name,
        IFNULL(upper_menu_no, 0) AS upper_menu_no,
        concat(menu_no) AS NO_PATH,
        concat('/', menu_code) AS MENU_PATH,
        1 AS DEPTH
    FROM tbl_menu
    WHERE IFNULL(upper_menu_no, 0) = 0
    UNION ALL
    SELECT
        m.menu_no,
        m.menu_name,
        IFNULL(m.upper_menu_no, 0),
        concat(C.NO_PATH, '->', m.menu_no) AS NO_PATH,
        concat(C.MENU_PATH, '/', m.menu_code) AS MENU_PATH,
        1 + C.DEPTH AS DEPTH
    FROM tbl_menu m
             INNER JOIN CTE C ON C.menu_no = m.upper_menu_no
)
SELECT
    CONCAT(LPAD('    ', 2*(CTE.DEPTH-1),' '), CTE.menu_no) AS menu_no,
    IFNULL(CTE.upper_menu_no, 0) AS upper_menu_no,
    CONCAT(LPAD('    ', 2*(CTE.DEPTH-1),' '),CTE.menu_name) AS menu_name,
    CTE.NO_PATH,
    CTE.MENU_PATH,
    #    RANK() OVER (PARTITION BY (CASE WHEN CTE.upper_menu_no IS NOT NULL THEN 1 ELSE 0 END) ORDER BY CTE.NO_PATH DESC) AS rank,
        #	DENSE_RANK() OVER (PARTITION BY (CASE WHEN upper_menu_no IS NOT NULL THEN 1 ELSE 0 END) ORDER BY NO_PATH DESC) AS dense_rank,
        #	ROW_NUMBER() OVER (PARTITION BY (CASE WHEN upper_menu_no IS NOT NULL THEN 1 ELSE 0 END) ORDER BY NO_PATH DESC) AS row_num,
        DEPTH
FROM CTE
ORDER BY NO_PATH
;

###########################################################################################

##############################################################################################
################################### FN_GET_DEPT_ORD 함수 ######################################
##############################################################################################

CREATE OR REPLACE FUNCTION FN_GET_DEPT_ORD( P_DNO INT, P_GNO INT) RETURNS INT
BEGIN
    DECLARE RET_ORD INT;
with recursive r_dept as (
    select dept_no, upper_dept_no, ord ,last_dno, gno
    from tbl_dept
    union all
    select d1.dept_no, d2.upper_dept_no, d2.ord, d2.last_dno , d2.gno
    from tbl_dept d1 inner join r_dept d2 on d2.dept_no = d1.last_dno
)
select IFNULL(MAX(ORD), 0)+1 INTO RET_ORD from r_dept where dept_no = P_DNO and gno = P_GNO;
-- RTN_VAL : 코드명(CODE_NM)
RETURN RET_ORD;
END;


##############################################################################################
################################### FN_INSERT_DEPT 함수 #######################################
##############################################################################################

create or replace
    function FN_INSERT_DEPT(
    t_company_no bigint, t_dept_name varchar(100), t_dept_desc varchar(1000), t_upper_dept_no bigint, t_gno bigint, t_depth bigint
) returns bigint
begin
    declare rtn_val bigint;

    ## 임의의 부서를 직접 추가
    insert into tbl_dept (company_no, dept_name, dept_desc, upper_dept_no, gno, depth, reg_user)
    values (t_company_no, t_dept_name, t_dept_desc, t_upper_dept_no, t_gno, t_depth+1, 'admin')
    ;

    ## 만든 함수를 이용해서 (상위부서 번호와 그룹번호를 사용)  ord값 조회
    # newdept_no 변수에 결과값을 저장
    ## 동일한 gno를 가지는 부서중에 조회한 ord값 보다 크거나 같은 모든 ord를 1씩 추가
    update tbl_dept set ord = ord + 1
    where gno = t_gno and ord >= FN_GET_DEPT_ORD(t_upper_dept_no,t_gno)
    ;

    ## 직접 추가한 부서에 위에서 조회한 ord값을 업데이트
    update tbl_dept set ord = FN_GET_DEPT_ORD(t_upper_dept_no,t_gno)
    where gno = t_gno and dept_no = LAST_INSERT_ID()
    ;

    ## 추가한 부서의 dept_no를 상위 부서의 last_dno에 업데이트
    update tbl_dept set last_dno = LAST_INSERT_ID()
    where gno = t_gno and dept_no = t_upper_dept_no
    ;

    select max(dept_no) into rtn_val
    from tbl_dept
    where gno = t_gno
    ;

    return rtn_val;
end;

##############################################################################################
################################ FN_GET_HIERARCHY_ORGA 함수 ###################################
##############################################################################################
create or replace
    function FN_GET_HIERARCHY_ORGA(t_orga_no bigint) returns varchar(1000)
begin
    declare rtn_val varchar(1000);

    WITH RECURSIVE CTE AS
    (
        SELECT
               orga_code
             , orga_no
             , orga_name
             , IFNULL(upper_orga_no, 0) AS upper_orga_no
             , CONCAT(orga_name) AS orga_path
             , orga_type
             , 0 depth
        FROM dmap
        WHERE orga_type = 'company'
        UNION ALL
        SELECT
               m.orga_code
             , m.orga_no
             , m.orga_name
             , m.upper_orga_no
             , IF(m.orga_type != 'emp', CONCAT(c.orga_path, '>', m.orga_name), c.orga_path) orga_path
             , m.orga_type
             , DEPTH+1
        FROM dmap m
        INNER JOIN CTE c ON c.orga_no = m.upper_orga_no
    ),
    dmap AS
    (
        SELECT CASE WHEN orga_type = 'company' THEN (SELECT c.company_no FROM tbl_company c WHERE c.orga_no = o.orga_no)
                    WHEN orga_type = 'dept' THEN (SELECT td.dept_no FROM tbl_dept td JOIN tbl_dept_mapping dm ON dm.dept_no = td.dept_no WHERE dm.orga_no = o.orga_no)
                    WHEN orga_type = 'emp' THEN (SELECT e.emp_no FROM tbl_emp e JOIN tbl_emp_mapping em ON em.emp_no = e.emp_no WHERE em.orga_no = o.orga_no)
                    ELSE '' END orga_code
             , CASE WHEN orga_type = 'company' THEN (SELECT c.company_name FROM tbl_company c WHERE c.orga_no = o.orga_no)
                    WHEN orga_type = 'dept' THEN (SELECT td.dept_name FROM tbl_dept td JOIN tbl_dept_mapping dm ON dm.dept_no = td.dept_no WHERE dm.orga_no = o.orga_no)
                    WHEN orga_type = 'emp' THEN (SELECT e.emp_name FROM tbl_emp e JOIN tbl_emp_mapping em ON em.emp_no = e.emp_no WHERE em.orga_no = o.orga_no)
                    ELSE '' END orga_name
             , o.orga_no
             , o.upper_orga_no
             , o.orga_type
        FROM tbl_orga o
    )
    SELECT orga_path into rtn_val
    FROM CTE
    WHERE orga_no = t_orga_no;

    return rtn_val;
end;

##############################################################################################
################################### FN_GET_ROLE_CNT 함수 ######################################
##############################################################################################
DELIMITER //
create or replace
    function FN_GET_ROLE_CNT(t_orga_no bigint, t_search_role varchar(200)) returns bigint
begin
    declare rtn_val bigint;
#     if t_search_role = null then set t_search_role = ''; END if;

    select sum(role_cnt) role_sum into rtn_val
    from
    (
         with recursive cte as (
             select *
             from tbl_orga
             where orga_no = t_orga_no
             union all
             select o.*
             from cte c
                      join tbl_orga o on o.orga_no = c.upper_orga_no
         )
         select cte.*
              , (
             select count(*) role_cnt
             from tbl_orga_role tor
                      left join tbl_role_group trg on tor.role_group_no = trg.role_group_no and trg.role_group_use = true
             where tor.orga_no = cte.orga_no
               and trg.role_group_name like concat('%', t_search_role, '%')
         ) role_cnt
         from cte
     ) r
    ;

    return rtn_val;
end//


select o.orga_no
     , FN_GET_ROLE_CNT(tor.orga_no, ${search_role})
     , ifnull(FN_GET_ROLE_CNT(tor.orga_no, ${search_role}), 0) roles
from tbl_orga o
left join tbl_orga_role tor on tor.orga_no = o.orga_no
left join tbl_role_group trg on tor.role_group_no = trg.role_group_no and trg.role_group_use = true
where o.orga_no in (1, 6, 12, 16, 34)
;

##############################################################################################

## testdb에 등록된 function 확인하는 쿼리
show function status where db = 'aquaranth1';

## 테스트 쿼리
select FN_INSERT_DEPT(
               ${company_no},
               ${dept_name},
               ${ddesc},
               ${upper_dept_no},
               ${gno},
               ${depth}
           );


select * from tbl_dept order by ord;
select * from tbl_company;

## 조직 계층정보 테스트 쿼리
select FN_GET_HIERARCHY_ORGA(37);

select FN_GET_HIERARCHY_ORGA(orga_no)
from tbl_orga;

##############################################################################################

select tdm.orga_no, d.dept_no, d.upper_dept_no, d.dept_name, tc.company_name, d.ord, d.gno, tc.company_no, d.last_dno, d.depth
from tbl_dept d
         join tbl_dept_mapping tdm on d.dept_no = tdm.dept_no
         join tbl_company tc on d.company_no = tc.company_no
order by gno, ord
;

select *
from tbl_role_group;

select *
from tbl_menu;

select *
from tbl_role_group;

## 조직(orga)테이블에서 조직번호(orga_no), 조직코드(회사/부서/사원의 no), 조직명(회사/부서/사원의 name), 상위조직번호(upper_orga_no), 조직타입(company, dept, emp)
select o.orga_no
     , case when o.orga_type = 'company' then (select c.company_no from tbl_company c where c.orga_no = o.orga_no and c.company_no = ${companyNo})
            when o.orga_type = 'dept' then (select d.dept_no from tbl_dept d join tbl_dept_mapping dM on d.dept_no = dM.dept_no where dM.orga_no = o.orga_no and d.dept_no = ${deptNo})
            when o.orga_type = 'emp' then (select e.emp_no from tbl_emp e join tbl_emp_mapping eM on e.emp_no = eM.emp_no where eM.orga_no = o.orga_no and e.username = ${username})
            else 0 end orga_code
     , case when o.orga_type = 'company' then (select c.company_name from tbl_company c where c.orga_no = o.orga_no and c.company_no = ${companyNo})
            when o.orga_type = 'dept' then (select d.dept_name from tbl_dept d join tbl_dept_mapping dM on d.dept_no = dM.dept_no where dM.orga_no = o.orga_no and d.dept_no = ${deptNo})
            when o.orga_type = 'emp' then (select e.emp_name from tbl_emp e join tbl_emp_mapping eM on e.emp_no = eM.emp_no where eM.orga_no = o.orga_no and e.username = ${username})
            else 0 end orga_name
     , o.upper_orga_no
     , o.orga_type
from tbl_orga o
where orga_type = 'emp'
;

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
where orga_type = 'emp'
;

select * from tbl_dept d join tbl_dept_mapping dM on d.dept_no = dM.dept_no where dM.orga_no = ${orga_no};



select case when o.orga_type = 'company' then (select c.company_name from tbl_company c where c.orga_no = o.orga_no)
            when o.orga_type = 'dept' then (select d.dept_name from tbl_dept d join tbl_dept_mapping dM on d.dept_no = dM.dept_no where dM.orga_no = o.orga_no)
            when o.orga_type = 'emp' then (select e.emp_name from tbl_emp e join tbl_emp_mapping eM on e.emp_no = eM.emp_no where eM.orga_no = o.orga_no)
            else 0 end orga_name
     , o.orga_no
     , case when o.orga_type = 'dept' then (select d.company_no from tbl_dept d join tbl_dept_mapping tdm on d.dept_no = tdm.dept_no where tdm.orga_no = o.orga_no)
            else 0 end dept_company
from tbl_orga o;

select *
from tbl_role_group;



select *# distinct m.menu_code
from (WITH RECURSIVE findMainOrga AS
    (select tbl_orga.orga_no, tbl_orga.upper_orga_no, tbl_orga.orga_type
    from tbl_orga
    inner join tbl_emp_mapping eM on tbl_orga.orga_no = eM.orga_no
    inner join tbl_emp e on eM.emp_no = e.emp_no
    join tbl_dept_mapping d on tbl_orga.upper_orga_no = d.orga_no
    where username = ${username}
    #and d.dept_main = true
    UNION ALL
    select o.orga_no, o.upper_orga_no, o.orga_type
    from findMainOrga f
    inner join tbl_orga o on f.upper_orga_no = o.orga_no)
    select f.orga_no, f.orga_type
    from findMainOrga f) as f
    left join tbl_orga_role o on o.orga_no = f.orga_no
    left join tbl_role_group rg on o.role_group_no = rg.role_group_no
    left join tbl_menu_role mr on rg.role_group_no = mr.role_group_no
    left join tbl_menu m on mr.menu_no = m.menu_no
order by f.orga_type
;

select * from tbl_orga_role;

WITH RECURSIVE findMainOrga AS
                   (select tbl_orga.orga_no, tbl_orga.upper_orga_no, tbl_orga.orga_type
                    from tbl_orga
                             inner join tbl_emp_mapping eM on tbl_orga.orga_no = eM.orga_no
                             inner join tbl_emp e on eM.emp_no = e.emp_no
                             join tbl_dept_mapping d on tbl_orga.upper_orga_no = d.orga_no
                    where username = ${username}
    #and d.dept_main = true
UNION ALL
select o.orga_no, o.upper_orga_no, o.orga_type
from findMainOrga f
         inner join tbl_orga o on f.upper_orga_no = o.orga_no)
select f.orga_no, f.orga_type
from findMainOrga f
;


###########################################################################################




###########################################################################################

select trg.role_group_no, trg.role_group_name, trg.company_no, tc.company_name
from tbl_company tc
left join tbl_role_group trg on trg.company_no = tc.company_no
where trg.role_group_use = true
  and tc.orga_no = ${orgaNo}
  and trg.role_group_name like concat('%', ${roleGroupSearch}, '%')
;

select trg.role_group_no, trg.role_group_name, trg.company_no, tc.company_name
from tbl_company tc
left join tbl_role_group trg on trg.company_no = tc.company_no
where trg.role_group_use = true and tc.orga_no = 21
;



select * from tbl_role_group;
select * from tbl_orga_role;
# insert into tbl_orga_role (role_group_no, orga_no, reg_user) values(1, 1, '준성');

##########################################################################################

###########################################################################################

###########################################################################################

###########################################################################################

###########################################################################################

###########################################################################################

