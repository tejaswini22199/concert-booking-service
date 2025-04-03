DELIMITER $$

CREATE PROCEDURE insert_seats()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE concert_id INT DEFAULT 1;
    DECLARE seat_type VARCHAR(20);
    
    WHILE i <= 1000 DO
        IF i % 2 = 0 THEN 
            SET seat_type = 'VIP';
        ELSE 
            SET seat_type = 'Regular';
        END IF;
        
        INSERT INTO seat (seat_number, is_available, seat_type, concert_id)  
        VALUES (CONCAT('S', i), true, seat_type, concert_id);
        
        SET i = i + 1;
        SET concert_id = IF(concert_id = 5, 1, concert_id + 1); -- Rotate concert IDs
    END WHILE;
END $$

DELIMITER ;

CALL insert_seats();

