**JBehave Stories and Spring-Boot Application**

Project is a reactor with two item-projects:

- Spring-Boot application
- JBehave stories to test the application

Spring-Boot Application is a "hello-word" WEB-application with empty WEB-Components, e.g. Contoller is there but it is empty

JBehaave srories are lighr-weit stories that only use the domain and business-logic parts of the application. The WEB-parts of the application are ignored

The Stories are activated as a standard JUnit test with the standard Spring-Boot test-runner. The Spring-Boot application of the stories is actually configured so that it is simple Java application