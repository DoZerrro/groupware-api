use aquaranth1;

#tbl_company insert

insert into tbl_orga
    (upper_orga_no, orga_type, reg_user)
values (null,
        'company',
        'admin')
;
insert into tbl_company
(orga_no, company_name, company_address, company_tel, owner_name, founding_date, business_number, company_use, reg_user)
values (last_insert_id(),
        'DOUZONE',
        '강원도 순천시 남산면 수동리 749',
        '02-6233-3000',
        '김용우',
        '2003-06-01',
        '134-81-08473',
        true,
        'admin')
;

insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (null,
        'company',
        'admin')
;
insert into tbl_company
(orga_no, company_name, company_address, company_tel, owner_name, founding_date, business_number, company_use, reg_user)
values (last_insert_id(),
        'MEGAZONE',
        '서울 강남구 논현로85길 46',
        '02-2109-2500',
        '장지황',
        '2001-03-01',
        '214-86-79342',
        true,
        'admin')
;

insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (null,
        'company',
        'admin')
;
insert into tbl_company
(orga_no, company_name, company_address, company_tel, owner_name, founding_date, business_number, company_use, reg_user)
values (last_insert_id(),
        'KAKAO',
        '제주특별자치도 제주시 첨단로 242',
        '1577-3321',
        '홍은택',
        '1995-02-01',
        '120-81-41578',
        false,
        'admin')
;

insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (null,
        'company',
        'admin')
;
insert into tbl_company
(orga_no, company_name, company_address, company_tel, owner_name, founding_date, business_number, company_use, reg_user)
values (last_insert_id(),
        'CJ',
        '서울특별시 중구 소월로2길 12',
        '02-726-8114',
        '손경식',
        '1953-08-01',
        '104-81-29553',
        true,
        'admin')
;

insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (null,
        'company',
        'admin')
;
insert into tbl_company
(orga_no, company_name, company_address, company_tel, owner_name, founding_date, business_number, company_use, reg_user)
values (last_insert_id(),
        'COUPANG',
        '서울특별시 송파구 송파대로 570',
        '1577-7011',
        '강한승',
        '2013-07-01',
        '120-88-03579',
        false,
        'admin')
;


########################################################################################################################
########################################################################################################################

#tbl_dept insert

########################################################################################################################
################################### FN_GET_DEPT_ORD 함수 ################################################################
########################################################################################################################
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

########################################################################################################################
################################### FN_INSERT_DEPT 함수 #################################################################
########################################################################################################################
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
########################################################################################################################
########################################################################################################################
# aquaranth1 에 등록된 function 확인하는 쿼리
show function status where db = 'aquaranth1';

#더존의 개발팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (1,
        'dept',
        'admin')
;

# tbl_orga insert 문이 실행되고 해당 테이블에서 orga_no 값을 받아온다.
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        1,
                        '개발팀',
                        '개발팀',
                        null,
                        1,
                        0)
        ),
        @orga_no,
        'admin'
       );


#메가존의 개발팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (2,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        2,
                        '개발팀',
                        '개발팀',
                        null,
                        2,
                        0)
        ),
        @orga_no,
        'admin'
       );


#카카오의 개발팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (3,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        3,
                        '개발팀',
                        '개발팀',
                        null,
                        3,
                        0)
        ),
        @orga_no,
        'admin'
       );


#CJ의 개발팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (4,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        4,
                        '개발팀',
                        '개발팀',
                        null,
                        4,
                        0)
        ),
        @orga_no,
        'admin'
       );


#쿠팡의 개발팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (5,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        5,
                        '개발팀',
                        '개발팀',
                        null,
                        5,
                        0)
        ),
        @orga_no,
        'admin'
       );
########################################################################################################################
#더존의 개발팀 -> 개발 1팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (6,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        1,
                        '개발1팀',
                        '개발1팀',
                        1,
                        1,
                        1)
        ),
        @orga_no,
        'admin'
       );

#더존의 개발팀 -> 개발2팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (6,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        1,
                        '개발2팀',
                        '개발2팀',
                        1,
                        1,
                        1)
        ),
        @orga_no,
        'admin'
       );

#더존의 개발팀 -> 개발 3팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (6,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        1,
                        '개발3팀',
                        '개발3팀',
                        1,
                        1,
                        1)
        ),
        @orga_no,
        'admin'
       );

#더존의 개발팀 -> 개발 3팀 -> 개발 3-1팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (12,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        1,
                        '개발3-1팀',
                        '개발3-1팀',
                        8,
                        1,
                        2)
        ),
        @orga_no,
        'admin'
       );

#더존의 개발팀 -> 개발 3팀 -> 개발 3-2팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (12,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        1,
                        '개발3-2팀',
                        '개발3-2팀',
                        8,
                        1,
                        2)
        ),
        @orga_no,
        'admin'
       );
########################################################################################################################
#CJ의 개발팀 -> 개발 1팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (8,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        4,
                        '개발1팀',
                        '개발1팀',
                        4,
                        4,
                        1)
        ),
        @orga_no,
        'admin'
       );

#CJ의 개발팀 -> 개발 2팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (8,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        4,
                        '개발2팀',
                        '개발2팀',
                        4,
                        4,
                        1)
        ),
        @orga_no,
        'admin'
       );

#CJ의 개발팀 -> 개발 1팀 -> 개발 1-1팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (13,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        4,
                        '개발1-1팀',
                        '개발1-1팀',
                        9,
                        4,
                        2)
        ),
        @orga_no,
        'admin'
       );
########################################################################################################################
#메가존의 인사팀
insert into tbl_orga
(upper_orga_no, orga_type, reg_user)
values (7,
        'dept',
        'admin')
;
set @orga_no = cast(last_insert_id() as int);
insert into tbl_dept_mapping
(dept_no, orga_no, reg_user)
values ((select FN_INSERT_DEPT(
                        2,
                        '인사팀',
                        '인사팀',
                        2,
                        2,
                        0)
        ),
        @orga_no,
        'admin'
       );
########################################################################################################################
########################################################################################################################

