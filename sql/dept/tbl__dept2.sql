
create table tbl_dept2(
                          dno int auto_increment primary key ,
                          dname varchar(100) not null,
                          gno int,
                          ord int default 0,
                          depth int default 0,
                          parent int,
                          lastDno int
);


INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (1, '더존', 1, 0, 0, null, 3);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (2, '영업1팀', 1, 1, 1, 1, 5);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (3, '영업2팀', 1, 8, 1, 1, 11);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (4, '영업1-1', 1, 2, 2, 2, 9);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (5, '영업1-2', 1, 6, 2, 2, null);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (6, '영업1-1-1', 1, 3, 3, 4, 8);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (7, '영업1-3', 1, 7, 2, 2, null);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (8, '영업1-1-1-1', 1, 4, 4, 6, null);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (9, '영업1-1-2', 1, 5, 3, 4, null);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (10, '영업2-1', 1, 9, 2, 3, null);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (11, '영업2-2', 1, 12, 2, 3, 14);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (12, '영업2-1-1', 1, 10, 3, 10, 13);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (13, '영업2-1-1-1', 1, 11, 4, 12, null);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (14, '영업2-2-1', 1, 13, 3, 11, 15);
INSERT INTO webdb.tbl_dept2 (dno, dname, gno, ord, depth, parent, lastDno) VALUES (15, '영업2-2-1-1', 1, 15, 4, 14, null);
