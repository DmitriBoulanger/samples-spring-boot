
Narrative:
In order to learn JBehave in the Spring-Boot Environment
As a CI test-developer
I want to define micro-service WEB-story

Scenario: Openning
Given story openning: Application 3 WEB

Scenario: Application 3 - Elasticsearch with REST-Service WEB-story

Given A3 server initialized
Then greeting
And admin

Scenario: Closing
Given story closing: Application 3 WEB
