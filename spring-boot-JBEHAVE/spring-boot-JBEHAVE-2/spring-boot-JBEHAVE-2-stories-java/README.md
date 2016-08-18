Integration Test Project - Non-WEB Units
========================================

Integration test in this project only include non-WEB components from the integrated units, 
e.g. such as domain models, cache operations, in-memory activities, etc. 
On the contrary, WEB-Components such as MVC, REST and other Spring-Boot controllers 
dependent on WEB-interfaces, are completely ignored in this integration test

Integration test involves only the application A1. The corresponding test-application 
and the test-itself can be seen as a Java applications. 
The test performs JBehave Story located in the test-resources.

This project is a standard Spring-Boot project but is significantly restricted 
to **pure** Java features, i.e. such things as MVC, REST, WebClient, etc. are  disabled