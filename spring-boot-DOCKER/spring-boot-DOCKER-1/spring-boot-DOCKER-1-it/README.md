## Integration Test for Jolokia

A*ttention: project requires custom life-cycle!*

  Call it with
  
 ` mvn verify`

 The test does the following:

  * Creates a Docker **data** container with **jolokia.war** and **jolokia-it.war**  as described in assembly descriptor src/main/docker-assembly.xml
  * Starts (and optionally pulls) the **jolokia/tomcat-7.0 container** with the above data containers linked to it
  * Waits until Tomcat is up (i.e. until it is reachable via an HTTP request)
  * Runs an integration test via *maven-failsafe-plugin*. The test uses rest-assured for accessing the deployed app.
  * Prints out some version information about the container running (in order prove that's not a fake)
  * Stops and removes the containers.

