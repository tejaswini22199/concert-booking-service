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

echo "🔥 All booking requests have been sent!"

