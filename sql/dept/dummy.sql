##
#	dept_no		dname	orga_no		upper_orga_no
#	1			개발팀	6			1
#	2			개발1팀	14			6
#	3			개발2팀	15			6
#	4			개발3팀	16			6
#	5			인사팀	17			1
#	6			인사1팀	18			17
#	7			인사2팀	19			17

insert into tbl_dept ( company_no, dname, dept_sort, ddesc)
values (1, '개발 1팀', 1, 'DEV 1');
insert into tbl_dept ( company_no, dname, dept_sort, ddesc)
values (1, '개발 2팀', 1, 'DEV 2');
insert into tbl_dept ( company_no, dname, dept_sort, ddesc)
values (1, '개발 3팀', 1, 'DEV 3');
insert into tbl_dept ( company_no, dname, dept_sort, ddesc)
values (1, '인사팀', 1, 'HR');
insert into tbl_dept ( company_no, dname, dept_sort, ddesc)
values (1, '인사 1팀', 1, 'HR 1');
insert into tbl_dept ( company_no, dname, dept_sort, ddesc)
values (1, '인사 2팀', 1, 'HR 2');

INSERT INTO orga (upper_orga_no) VALUES (6);
INSERT INTO orga (upper_orga_no) VALUES (6);
INSERT INTO orga (upper_orga_no) VALUES (6);
INSERT INTO orga (upper_orga_no) VALUES (1);
INSERT INTO orga (upper_orga_no) VALUES (17);
INSERT INTO orga (upper_orga_no) VALUES (17);

INSERT INTO deptMapping (dept_no, orga_no) VALUES (2, 14);
INSERT INTO deptMapping (dept_no, orga_no) VALUES (3, 15);
INSERT INTO deptMapping (dept_no, orga_no) VALUES (4, 16);
INSERT INTO deptMapping (dept_no, orga_no) VALUES (5, 17);
INSERT INTO deptMapping (dept_no, orga_no) VALUES (6, 18);
INSERT INTO deptMapping (dept_no, orga_no) VALUES (7, 19);
