CREATE TABLE seat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seat_number VARCHAR(10),
    is_available BOOLEAN,
    seat_type VARCHAR(50),
    concert_id BIGINT
);

INSERT INTO seat (seat_number, is_available, seat_type, concert_id)
SELECT CONCAT('S', ROW_NUMBER() OVER (ORDER BY RAND())), TRUE, 'Regular', 1 
FROM information_schema.tables LIMIT 1000;