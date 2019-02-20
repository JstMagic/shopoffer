# shopoffer
Aim : To create an offer to be shared amongst customers. This offer will automatically expired and can be cancelled at anytime before it expires. The shop admin will be able to create an offer along with its expiring date.

# Tech Used
  Spring Boot 2.1.3
  Swagger UI Api Documentation
  Tomcat Embeded Server included in spring boot
  H2 Embeded Database
  JUnit and Mockito for testing
  

# Implementation and changes
 - Created a RestFul web service with different endpoints for customer to carryout a CRUD operation
 - Created a test class to test the service layer
 - Create a single page html to quickly create an offer, the offer is then created and successfull the offer will be displayed
 - Added Swagger API Documentation 

* How does an offer automatically expire?
  (1) it checks if an offer is still valid based on the expiration date > current timestamp (then offer expired) when you make an api call to it but this code was commented out so I could implement a different method
  (2) created a ScheduledTasks which runs every 5 seconds to check if an offer is still valid and if its invalid it flags the offer in the database field (valid) to false, if an offer is already canceled then it doesn't mark this offer

# What i didnt do
create other html offer pages to carryout cruid opperations

# Running Demo

* Method 1
Step 1
I have also uploaded the Jar file to google drive https://drive.google.com/file/d/1YVvTfCRAQq3zq3E-Ps_CbH8QXxY6Zzd2/view?usp=sharing
in the same directory open bash or cmd and if you have maven installed run the following command java -jar shopoffer-0.0.1-SNAPSHOT.jar mvn spring-boot:run

Step 2
When its running visit localhost:8080 and the hmtl offer creation page should appear

to test all endpoints such as updating an offer or retrieving an offer this can be tested on Swaggger by going to 
http://localhost:8080/swagger-ui.html to test all the endpoints 

* Method 2
open your favourite java ide and create a new project from this git repository
setup your ide by supplying the jDK 1.8+ and ensure maven is synced with the project
run the maven clean and build command, mvn clean compile
to start the project you can either click run on your ide after setting up project or run mvn spring-boot:run

Step 2
When its running visit localhost:8080 and the hmtl offer creation page should appear

to test all endpoints such as updating an offer or retrieving an offer this can be tested on Swaggger by going to 
http://localhost:8080/swagger-ui.html to test all the endpoints 



