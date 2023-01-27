select * from tbl_orga;

select * from tbl_dept;

select * from tbl_dept_mapping;

select * from tbl_emp;

select * from tbl_company;

delete from tbl_dept_mapping where dept_no in (23,24,25);

delete from tbl_dept where dept_no in (23,24,25);

select * from tbl_dept where company_no = 1 order by ord;


insert into tbl_orga (upper_orga_no, orga_type, reg_user)
values (1, 'dept','admin')
;

select * from tbl_dept_mapping;

# insert into tbl_dept_mapping (dept_no, orga_no, dept_main, reg_user)
# values (#{deptNo}, #{orgaNo}, '0', #{regUser})

CREATE OR REPLACE FUNCTION FN_GET_DEPT_ORD( P_DNO INT, P_GNO INT) RETURNS INT

BEGIN

    DECLARE RET_ORD INT;

with recursive r_dept as (

    select dept_no, upper_dept_no, ord , last_dno, company_no
    from tbl_dept

    union all

    select d1.dept_no, d2.upper_dept_no , d2.ord, d2.last_dno , d2.company_no
    from tbl_dept d1 inner join r_dept d2 on d2.dept_no = d1.last_dno
)

select
    CASE WHEN ord is null THEN (select max(ord)+1 from tbl_dept td where td.company_no =P_GNO ) ELSE max(ord) +1 END INTO RET_ORD
from r_dept where dept_no = P_DNO and company_no = P_GNO;

-- RTN_VAL : 코드명(CODE_NM)
RETURN RET_ORD;
END;


select CASE WHEN FN_GET_DEPT_ORD(1,17) IS null THEN max(ord) +1 END from tbl_dept where tbl_dept.company_no = 1 ;

select FN_GET_DEPT_ORD(6,1)
;

select FN_GET_DEPT_ORD(null,1)
;

select company_no, orga_no, company_name , null upper_dept_no, 0 ord, 0 depth
from
    tbl_company cp where company_no = 1
;


select *
from
    (select company_no, orga_no, company_name , null upper_dept_no, 0 ord, 0 depth
     from
         tbl_company cp where company_no = 1 ) cp inner join tbl_dept dept where dept.company_no = cp.company_no;


select cp.company_no,company_name, cp.orga_no, dept.dept_no, dept.dept_name, dept.ord, dept.depth, dept.last_dno
from
    (select company_no, orga_no, company_name , null upper_dept_no, 0 ord, 0 depth
     from
         tbl_company cp where company_no = 1 ) cp right outer join tbl_dept dept on cp.company_no = dept.company_no
;


select null dept_no, orga_no, company_name dept_name, null upper_dept_no, 0 ord, 0 depth
from
    tbl_company cp where company_no = 1
union all
select dept_no, (select orga_no from tbl_dept_mapping dm where dm.dept_no = dept.dept_no) orga_no, dept_name, upper_dept_no, ord, depth
from tbl_dept dept where company_no = 1
order by ord
;


create or replace view v_dept_tree as
    (
    select null dept_no, orga_no, company_name dept_name, null upper_dept_no, 0 ord, 0 depth, company_no, null last_dno
    from
        tbl_company cp
    union all
    select dept.dept_no, tdm.orga_no, dept_name, upper_dept_no,ord,depth, company_no, last_dno
    from
        tbl_dept_mapping tdm inner join
        (select dept_no, dept_name, upper_dept_no, ord, depth,company_no, last_dno
         from tbl_dept dept  ) dept
        on tdm.dept_no = dept.dept_no or tdm.dept_no is null
    )
;

select * from v_dept_tree where company_no = 1 order by ord ;

select * from tbl_dept_mapping
;

select * from tbl_orga;

select null dept_no, orga_no, company_name dept_name, null upper_dept_no, 0 ord, 0 depth
from
    tbl_company cp where company_no = 1
union all
select dept_no, (select orga_no from tbl_dept_mapping dm where dm.dept_no = dept.dept_no) orga_no, dept_name, upper_dept_no, ord, depth
from tbl_dept dept where company_no = 1
order by ord
;

select null dept_no, orga_no, company_name dept_name, null upper_dept_no, 0 ord, 0 depth, null last_dno
from
    tbl_company cp where company_no = 1
union all
select dept.dept_no, tdm.orga_no, dept_name, upper_dept_no,ord,depth, last_dno
from
    tbl_dept_mapping tdm inner join
    (select dept_no, dept_name, upper_dept_no, ord, depth, last_dno
     from tbl_dept dept where company_no = 1 ) dept
    on tdm.dept_no = dept.dept_no
;


select
    dept_no, orga_no, dept_name, upper_dept_no, ord, depth, company_no
from
    v_dept_tree
where company_no = 1 order by ord ;


select *
from tbl_dept;

insert into  tbl_dept (upper_dept_no, company_no, dept_name, del_flag, main_flag, dept_desc,  depth, reg_user, reg_date)
values (12, 1, '개발3-1-1팀', true, true, '개발3-1-1팀',1,4, 'admin', now());


commit;


select
    dept_no, orga_no, dept_name, upper_dept_no, ord, depth, company_no, last_dno
from
    v_dept_tree

where company_no = 1
;
