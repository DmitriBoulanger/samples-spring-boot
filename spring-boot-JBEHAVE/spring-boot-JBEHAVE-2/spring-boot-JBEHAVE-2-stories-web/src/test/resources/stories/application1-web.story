
Narrative:
In order to learn JBehave in the Spring-Boot Environment
As a CI test-developer
I want to define micro-service WEB-story

Scenario: Application 1 - Shopper Management with the WEB-Service

Given A1 server initialized
When new shopper created
Then created shopper found
And unknown shopper not found

