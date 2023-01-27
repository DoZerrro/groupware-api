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
