Integration Test Project - All Units
====================================

Integration test in this project includes most of components from the integrated resources, 
e.g. such as domain models, cache operations, in-memory activities, 
REST and other Spring-Boot controllers that are directly dependent on WEB-interfaces. 

Integration test involves the applications A1, A2 and A3. 
The corresponding test-application and the test-itself can be seen 
as complete Spring-Boot application/test. 
The test uses several JBehave Stories located in the test-resources.
These stories are running using maximal 1o threads

This project a standard non-restricted Spring-Boot project, 
i.e. such things as MVC, REST, WebClient, Data Repositories. etc. 
are in action and they are involved in the integration test