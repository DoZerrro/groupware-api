WITH RECURSIVE CTE AS (
#     최상위 메뉴만 꺼냄.
    SELECT menu_no,
           menu_name,
#         IFNULL = 상위번호가 null이면, 0으로 처리
           IFNULL(upper_menu_no, 0) AS upper_menu_no,
#         데이터 형태 변경 숫자에서 문자로 변경
#         CAST(menu_no as char(100) character set utf8) AS ID_PATH,
           CONCAT(menu_code)        as URL,
#         강제로 1 집어넣음...
           1                        AS CLEVEL
    FROM menu
#     0번부터 전체 탐색
    WHERE IFNULL(upper_menu_no, 0) = 0


    UNION ALL

#     뭔지 잘 모르겠다.. 동작은 위에 쿼리로 돌아간다..
    SELECT m.menu_no,
           m.menu_name,
           IFNULL(m.upper_menu_no, 0),
           concat(C.URL, '/', m.menu_code) AS id_path,
           1 + C.CLEVEL                    AS CLEVEL
    FROM menu m
             INNER JOIN CTE C ON C.menu_no = m.upper_menu_no)
SELECT CONCAT(LPAD('    ', 2 * (CLEVEL - 1), ' '), menu_no)   AS menu_no,
       IFNULL(upper_menu_no, 0)                               AS upper_menu_no,
       CONCAT(LPAD('    ', 2 * (CLEVEL - 1), ' '), menu_name) AS menu_name,
       URL,
       CLEVEL
FROM CTE
ORDER BY URL
;
