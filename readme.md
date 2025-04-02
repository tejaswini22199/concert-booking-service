# Concert Booking Service

## Setup
1. Clone the repository
2. Run `mvn clean install`
3. Start the application: `mvn spring-boot:run`
4. API Endpoints:
   - `POST /api/book` – Book seats
   - `POST /api/payment` – Make payment
   - `GET /api/paymentStatus/{bookingId}` – Check payment status
