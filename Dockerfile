FROM openjdk:latest
ADD target/CalendarSlotBookingservice-0.0.1-SNAPSHOT.jar CalendarSlotBookingservice-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD [ "java", "-jar", "CalendarSlotBookingservice-0.0.1-SNAPSHOT.jar" ]