  
 ## Various network modes

  Call it with 
  
  `mvn install` 
  
  and one of the following profiles:

  * "**container**" : Container connecting to another container's network
  * "**bridge**"    : Bridge mode
  * "**host**"      : Host mode
  * "**custom**"    : Custom network *test-network*

  
  **Attention**: Custom network **test-network** must be created with
  
  `docker network create test-network`
  
  in advance


