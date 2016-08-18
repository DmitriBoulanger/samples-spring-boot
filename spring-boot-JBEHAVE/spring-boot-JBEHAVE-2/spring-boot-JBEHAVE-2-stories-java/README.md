Integration Test Project - Non-WEB Units
========================================

Integration tests only include non-WEB components from the integrated resources, e.g. such as domain models, cache operations, in-memory activities, etc. On the contrary, WEB-Components are MVC, REST and other Spring-Boot controllers that are directly dependent on WEB-interfaces. Such components and their functionality are completely ignored in this integration test

Integration test involves only the application A1. The corresponding test-application and the test-itself can be seen as simple Java applications. The test is a JBehave Story located in the test-resources.

This project a standard Spring-Boot project that is significantly restricted to **pure** Java features, i.e. such things as MVC, REST, WebClient, etc. are completely disabled