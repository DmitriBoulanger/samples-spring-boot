
Narrative:
In order to learn JBehave in the Spring-Boot Environment
As a CI test-developer
I want to define micro-service WEB-story

Scenario: Customer Service WEB-story

Given server initialized
When new customer created
Then created customer found
And unknown customer not found

