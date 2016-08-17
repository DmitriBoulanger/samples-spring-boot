Story: JBehave and Spring-Boot WEB-application

Narrative:
In order to learn JBehave with Spring-Boot Environment
As a test-developer
I want to define sample story

Lifecycle:
Before:
Given product DDD with SKU 1234
And product DDD price is 35 EUR

Given product BDD with SKU 2345
And product BDD price is 30 EUR

Scenario: Empty shopping cart

Given empty shopping cart
Then shopping cart is empty

Scenario: Products are added to empty shopping cart

Given empty shopping cart
When products are added to the shopping cart:
|PRODUCT	|QTY |
|DDD		|	1|
|BDD		|	2|

Then the number of products in shopping cart is 2
And total price is 95 EUR
