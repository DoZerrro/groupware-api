WITH RECURSIVE CTE AS (SELECT menu_no,
                              menu_name,
                              icon_url,
                              menu_sort,
                              IFNULL(upper_menu_no, 0) AS upper_menu_no,
                              menu_code                as URL,
                              1                        AS depth,
                              default_menu_code,
                              menu_use,
                              menu_code
                       FROM menu
                       WHERE IFNULL(upper_menu_no, 0) = 0
                       UNION ALL
                       SELECT m.menu_no,
                              m.menu_name,
                              m.icon_url,
                              m.menu_sort,
                              IFNULL(m.upper_menu_no, 0),
                              concat(C.URL, '/', m.menu_code) AS id_path,
                              1 + C.depth                     AS CLEVEL,
                              m.default_menu_code,
                              m.menu_use,
                              m.menu_code
                       FROM menu m
                                INNER JOIN CTE C ON C.menu_no = m.upper_menu_no)
SELECT menu_no                  AS menu_no,
       menu_name                AS menu_name,
       icon_url,
       menu_sort,
       IFNULL(upper_menu_no, 0) AS upper_menu_no,
       URL,
       depth,
       default_menu_code,
       menu_use,
       menu_code
FROM CTE
ORDER BY URL
;
