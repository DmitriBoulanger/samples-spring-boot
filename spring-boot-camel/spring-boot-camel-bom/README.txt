
origin: https://github.com/apache/camel/blob/camel-2.17.x/parent/pom.xml

spring-boot: version 1.3.7.RELEASE
camel:       version 2.17.2

==========================================================================
                Changes/Updates
==========================================================================

1. Version variables
====================
    <!-- optional dependencies (from origin-parent) -->
	<jaxb-version>2.2.11</jaxb-version>
	
2. Original Maven variables
===========================
The properties item	
      <origin-project.version>2.17.2</origin-project.version> 
added and the global replacement performed
      ${project.version} => ${origin-project.version}
    
3. Plug-in management
===========================