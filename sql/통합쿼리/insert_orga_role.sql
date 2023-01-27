########################################################################################################################
#tbl_orga_role

##더존의 기본권한
insert into tbl_orga_role (role_group_no, orga_no, reg_user)
VALUES (1, 1, 'admin');

#더존의 개발팀 권한
insert into tbl_orga_role (role_group_no, orga_no, reg_user)
VALUES (2, 6, 'admin');

#더존의 개발 3-1팀 권한
insert into tbl_orga_role (role_group_no, orga_no, reg_user)
VALUES (3, 16, 'admin');

#더존의 담당자 권한


#CJ의 개발팀 권한
insert into tbl_orga_role (role_group_no, orga_no, reg_user)
VALUES (5, 13, 'admin');
