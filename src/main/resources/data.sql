CREATE TABLE seat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seat_number VARCHAR(10),
    is_available BOOLEAN,
    seat_type VARCHAR(50),
    concert_id BIGINT
);
 INSERT INTO seat (seat_number, is_available, seat_type, concert_id)
    -> SELECT CONCAT('S', ROW_NUMBER() OVER()), 
    ->        b'1', -- `b'1'` for BIT(1) (true)
    ->        CASE WHEN RAND() > 0.5 THEN 'VIP' ELSE 'Regular' END, 
    ->        1 -- Concert ID
    -> FROM (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) AS t1,
    ->      (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) AS t2,
    ->      (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) AS t3,
    ->      (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) AS t4;
Query OK, 625 rows affected (0.02 sec)
Records: 625  Duplicates: 0  Warnings: 0
