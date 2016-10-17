docker run -d 

-e SPRING_PROFILES_ACTIVE=ddev 
--network=ddev_net 
--name=case-service response-it/case-service:latest