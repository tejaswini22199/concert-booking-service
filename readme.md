# Concert Booking Service

### Setup
1. Clone the repository

### Build and Run the project 
* rm -rf target
* Run `mvn clean install`
* Start the application: `mvn spring-boot:run`

### API Endpoints:
   - `POST /api/book` – Book seats
   - `POST /api/payment` – Make payment
   - `GET /api/paymentStatus/{bookingId}` – Check payment status

### APIS 

#### API 1: /api/book 

#### Request: 

```
curl -X POST "http://localhost:8080/api/book" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNzQzNjc0MjU0LCJleHAiOjE3NDM2Nzc4NTR9.JTj9n_lz2ojKONXoHA9V8H6N-u4qYN3m-V2mIvTJMGI" \
-d '{
    "userId": 1,
    "numberOfSeats": 2,
    "seats": [
        {"id": 101},
        {"id": 102}
    ],
    "paymentStatus": "PENDING"
}'
```

#### Response: 

```
{"id":7,"userId":1,"numberOfSeats":2,"seats":[{"id":101,"seatNumber":null,"seatType":null,"concert":null,"available":false},{"id":102,"seatNumber":null,"seatType":null,"concert":null,"available":false}],"paymentStatus":"PENDING","seatIds":[101,102]}%   
```

#### Request: 

```                                                                                          
curl -X POST "http://localhost:8080/api/book" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNzQzNjc0MjU0LCJleHAiOjE3NDM2Nzc4NTR9.JTj9n_lz2ojKONXoHA9V8H6N-u4qYN3m-V2mIvTJMGI" \
-d '{
    "userId": 1,
    "numberOfSeats": 2,
    "seats": [
        {"id": 101},
        {"id": 102}
    ],
    "paymentStatus": "PENDING"
}'

```

#### response: 

```
{"timestamp":"2025-04-03T10:26:59.478+00:00","status":409,"error":"Conflict","path":"/api/book"}%            
```