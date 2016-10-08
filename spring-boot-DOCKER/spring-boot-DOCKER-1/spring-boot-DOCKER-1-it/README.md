## Integration Test for Jolokia

  Call it with
  
 ` mvn verify`

 The test does the following:

  * Creates a Docker **data** container with **jolokia.war** and **jolokia-it.war**  as described in assembly descriptor src/main/docker-assembly.xml
  * Starts (and optionally pulls) the **jolokia/tomcat-7.0 container** with the data container linked to it
  * Waits until Tomcat is up (i.e. until it is reachable via an HTTP request)
  * Runs an integration test via *maven-failsafe-plugin*, using rest-assured for accessing the deployed app.
  * Prints out some version information about the container running (in order prove that's not a fake)
  * Stops and removes the containers.

