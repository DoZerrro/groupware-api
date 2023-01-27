create table tbl_dept
(
    deptNo      bigint auto_increment
        primary key,
    upperDeptNo bigint                                    null,
    dname       varchar(200)                              not null,
    delflag     tinyint(1)    default 0                   not null,
    mainflag    tinyint(1)    default 1                   not null,
    deptSort    int                                       not null,
    ddesc       varchar(1000) default ''                  null,
    gno         int                                       null,
    ord         int                                       null,
    depth       int                                       null,
    regdate     timestamp     default current_timestamp() not null,
    updatedate  timestamp     default current_timestamp() not null,
    lastDno     int                                       null,
    constraint FK_dept_TO_dept_1
        foreign key (upperDeptNo) references tbl_dept (deptNo)
);

select *
from tbl_dept;

select *
from tbl_dept
order by ord;

drop table tbl_dept;

