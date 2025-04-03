# Concert Booking Service

### Setup

1. Fork and Clone the repository - 

```
git clone https://github.com/tejaswini22199/concert-booking-service.git
```

2. Install Mysql 

```
brew install mysql
brew services start mysql
mysql -u root -p
```

* Load the seats data into concert_booking dB table 

```
mysql -u root -p concert_booking < data.sql
```

### Build and Run the project 

* rm -rf target
* Run `mvn clean install`
* Start the application: `mvn spring-boot:run`

### API web server Info

host - localhost
port - 8080

### API Endpoints:
   - `POST /api/book` – Book seats
   - `POST /api/payment` – Make payment
   - `GET /api/paymentStatus/{bookingId}` – Check payment status

### APIs

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

* When Concurrent requests are sent: 

#### Request: 

Create a bash script with the following data and send concurrent requests using curl. First request is successful but the second request is rejected 


```
#!/bin/bash

# Define API URL
URL="http://localhost:8080/api/book"

# Define Authorization Tokens
TOKEN1="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBleGFtcGxlLmNvbSIsImlhdCI6MTc0MzY4MzM5MCwiZXhwIjoxNzQzNjg2OTkwfQ.pyllZ13OB7ZQXnqJvoWy6uT9O2BOp5jq70gSFo1GA4E"
TOKEN2="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMkBleGFtcGxlLmNvbSIsImlhdCI6MTc0MzY4MzM3NCwiZXhwIjoxNzQzNjg2OTc0fQ.6mBDkcv2ZYWjSFixeauChxa0CvSp1yvH-8FiYP-3zEA"

# Fixed User IDs
USER_ID1=8
USER_ID2=9

# Generate random seat IDs
SEAT_ID1=$((RANDOM % 200 + 1))
SEAT_ID2=$((RANDOM % 200 + 1))

# Randomly pick a payment status
PAYMENT_STATUS=("PENDING" "CONFIRMED" "FAILED")
STATUS1=${PAYMENT_STATUS[$RANDOM % 3]}
STATUS2=${PAYMENT_STATUS[$RANDOM % 3]}

# Define JSON Payloads with fixed user IDs
DATA1=$(cat <<EOF
{
    "userId": $USER_ID1,
    "numberOfSeats": 1,
    "seats": [{"id": $SEAT_ID1}],
    "paymentStatus": "$STATUS1"
}
EOF
)

DATA2=$(cat <<EOF
{
    "userId": $USER_ID2,
    "numberOfSeats": 1,
    "seats": [{"id": $SEAT_ID2}],
    "paymentStatus": "$STATUS2"
}
EOF
)

# Function to send request
send_request() {
  local TOKEN=$1
  local DATA=$2
  curl -X POST "$URL" \
       -H "Authorization: Bearer $TOKEN" \
       -H "Content-Type: application/json" \
       -d "$DATA" &
}

# Send requests concurrently
send_request "$TOKEN1" "$DATA1"
send_request "$TOKEN2" "$DATA2"

# Wait for all requests to finish
wait

```

#### Response: 

```
{"id":10,"userId":9,"numberOfSeats":1,"seats":[{"id":130,"seatNumber":null,"seatType":null,"concert":null,"available":false}],"paymentStatus":"PENDING","seatIds":[130]}
```

* second one returns status code as 409 as the seats are already locked for first user 

```

{"timestamp":"2025-04-03T13:11:42.416+00:00","status":409,"error":"Conflict","path":"/api/book"}
```

