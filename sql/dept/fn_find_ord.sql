#==================================함수===========================================

CREATE OR REPLACE FUNCTION FN_GET_DEPT_ORD( P_DNO INT, P_GNO INT) RETURNS INT

BEGIN

    DECLARE RET_ORD INT;

with recursive r_dept as (

    select deptNo, upperDeptNo, ord , lastDno, gno
    from tbl_dept

    union all

    select d1.deptNo, d2.upperDeptNo upperDeptNo, d2.ord, d2.lastDno , d2.gno
    from tbl_dept d1 inner join r_dept d2 on d2.deptNo = d1.lastDno
)

select MAX(ORD) +1 INTO RET_ORD from r_dept where deptNo = P_DNO and gno = P_GNO;

-- RTN_VAL : 코드명(CODE_NM)
RETURN RET_ORD;
END;

select FN_GET_DEPT_ORD(8,1) ;








#-------------------예시(연습)------------------------------
#부서 자체 추가  - 부모8 자원 1-1-1-1팀

select * from tbl_dept3;

select * from tbl_dept3 order by ord;

insert into tbl_dept3 (dname,gno,depth,parent)
values('자원 1-1-1-1팀', 1, 4, 8);

#ord값을 구해서 다른 부서의 ord값 변경

#4

select FN_GET_DEPT_ORD(8,1);

update tbl_dept3 set ord = ord + 1 where gno = 1 and ord >=  4;

#추가된 부서의 ord를 변경

update tbl_dept3 set ord = 4 where dno = 9;

#부모의 lastDno를 변경

update tbl_dept3 set lastDno = 9 where dno = 8;





#-------------------------------------------------
#============================더미데이터=============================
drop table tbl_dept3;

create table tbl_dept3(
                          dno int auto_increment primary key ,
                          dname varchar(100) not null,
                          gno int,
                          ord int default 0,
                          depth int default 0,
                          parent int,
                          lastDno int
);


INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (1, '더존', 1, 0, 0, null, 2);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (2, '자원1팀', 1, 1, 1, 1, 7);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (3, '자원2팀', 1, 10, 1, 1, null);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (4, '자원 1-1팀', 1, 2, 2, 2, 9);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (5, '자원 1-2팀', 1, 7, 2, 2, null);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (6, '자원 1-1-1팀', 1, 3, 3, 4, 10);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (7, '자원 1-3팀', 1, 8, 2, 2, 11);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (8, '자원 1-1-1-1팀', 1, 4, 4, 6, null);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (9, '자원 1-1-2팀', 1, 6, 3, 4, null);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (10, '자원 1-1-1-2팀', 1, 5, 4, 6, null);
INSERT INTO webdb.tbl_dept3 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (11, '자원 1-3-1팀', 1, 9, 3, 7, null);
