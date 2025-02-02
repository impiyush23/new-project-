-- Create the reservations table
CREATE TABLE IF NOT EXISTS reservations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    passenger_name TEXT NOT NULL,
    flight_number TEXT NOT NULL,
    seat_number TEXT NOT NULL
);

-- Insert a new reservation
INSERT INTO reservations (passenger_name, flight_number, seat_number) 
VALUES ('John Doe', 'FL123', '12A');

-- View all reservations
SELECT * FROM reservations;

-- Update a reservation
UPDATE reservations 
SET seat_number = '14B' 
WHERE id = 1;

-- Cancel (delete) a reservation
DELETE FROM reservations 
WHERE id = 1;

-- Example queries for more advanced operations:

-- Find all reservations for a specific flight
SELECT * FROM reservations 
WHERE flight_number = 'FL123';

-- Count the number of reservations for each flight
SELECT flight_number, COUNT(*) as reservation_count 
FROM reservations 
GROUP BY flight_number;

-- Find passengers with multiple reservations
SELECT passenger_name, COUNT(*) as reservation_count 
FROM reservations 
GROUP BY passenger_name 
HAVING COUNT(*) > 1;

-- Find available seat numbers for a specific flight
-- (Assuming we have a seats table with all possible seats)
SELECT s.seat_number
FROM seats s
LEFT JOIN reservations r ON s.seat_number = r.seat_number AND r.flight_number = 'FL123'
WHERE r.id IS NULL AND s.flight_number = 'FL123';

-- Add an index to improve query performance
CREATE INDEX idx_flight_number ON reservations(flight_number);

