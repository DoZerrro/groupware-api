ALTER TABLE company
    COLLATE='utf8mb4_general_ci',
    CONVERT TO CHARSET utf8mb4;

ALTER TABLE tbl_dept
    COLLATE='utf8mb4_general_ci',
    CONVERT TO CHARSET utf8mb4;

ALTER TABLE emp
    COLLATE='utf8mb4_general_ci',
    CONVERT TO CHARSET utf8mb4;

ALTER TABLE empmapping
    COLLATE='utf8mb4_general_ci',
    CONVERT TO CHARSET utf8mb4;

ALTER TABLE menu
    COLLATE='utf8mb4_general_ci',
    CONVERT TO CHARSET utf8mb4;

alter table role_group
    collate = 'utf8mb4_general_ci',
    convert to charset utf8mb4;
