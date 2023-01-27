create table tbl_dept2 (select * from tbl_dept);

select * from tbl_dept2;

drop table tbl_dept2;

create table tbl_dept2(
                          dno int auto_increment primary key ,
                          dname varchar(100) not null,
                          gno int,
                          ord int default 0,
                          depth int default 0,
                          parent int,
                          lastDno int
);

select * from tbl_dept2;

drop table tbl_dept2;

select * from tbl_dept2 order by ord;


insert into tbl_dept2 (dname, gno, depth, parent) values('영업2-2-1',1,3,11);

select last_insert_id(); ##14

select case when lastDno is null then ord+1 else 0 end ord
from
    tbl_dept2 where dno = 11
    ## 13

update tbl_dept2 set ord = ord + 1 where ord >= 13;

update tbl_dept2 set ord = 13 where dno = 14;

update tbl_dept2 set lastDno = 14 where dno = 11;

##

insert into tbl_dept2 (dname, gno, depth, parent) values('영업2-2-1-1',1,4,14);

select last_insert_id(); ##15

select case when lastDno is null then ord+1 else 0 end ord
from
    tbl_dept2 where dno = 14
    ## 15

update tbl_dept2 set ord = ord + 1 where ord >= 15;

update tbl_dept2 set ord = 15 where dno = 15;

update tbl_dept2 set lastDno = 15 where dno = 14;

