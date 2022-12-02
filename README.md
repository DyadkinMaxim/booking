## 1account test task
Maxim Dyadkin

## Task Requiremenents
  ### Components:
  - Booking component:
    - Retrieve all bookings on particular date
    - Retrieve one booking by its id
    - Add new Booking
    - Delete Booking

## Software Prerequisites (latest versions preferable)
  - Java 11
  - Maven 
  - Intellij Idea
  - H2 driver
  - free port 8080
  - Postman(Optional)

## How to Run the project
  - Run OneAccountApplication
    - Top Intellij panel -> Run/Debug Configurations -> run "OneAccountApplication"
    - Set up may be validated as message appears in the log: "Started OneAccountApplication in X seconds (JVM running for Y)"
    - The app is running now :)
   
## How to Test the project
 - There is single Integration test: BookingControllerTest. It is responsible for testing 
  corresponding business functionality. I think creating unit tests is redundant actitity as the project volume is not really big.
 - Tests may be run in manual way(run inside each test) or via Maven Intellij plugin(choose 1account-task -> Lifecycle -> test)
 - Also manual tests are available - Postman collection @one-account-task.postman_collection is located in the root of the project
     so this JSON may be imported in Postman and some operations can be tested there manually. 

## Workflow
 - General workflow regarding the task: user can make CRUD operations with Booking.
 - Dates are received in a sensible format: ISO 8601 (YYYY-MM-DD) which may be simply converted from String to LocalDate.
 - The key idea to support data consistency under heavy traffic is using unique index SEAT_UI in Booking table:
   index between SEAT_NUMBER, FILM_ID and BOOKING_DATE forbids adding duplicated bookings.
 - For viewing @Slf4j logs it is neccessary to change in application.properties:
 logging.level.root=INFO to logging.level.root=DEBUG  
 - Any application data may be viewed at http:localhost:8080/h2-console.
 - JDBC URL: jdbc:h2:mem:booking
 - Username: sa, empty password
 - In case of any questions feel free to ask Me: dyadkinm@gmail.com