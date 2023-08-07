# FamilyGathering2

## Set Up
ensure application.properties file is set up befire running project 
spring.datasource.url = jdbc:postgresql://localhost:5432/<ServerName>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.jpa.hibernate.ddl-auto=update
 `#`spring.jpa.hibernate.ddl-auto=create

code below will allow tomcat to give you it's own errors
server.error.whitelabel.enabled=false

Code below for enabling PUT and Delete Mapping
spring.mvc.hiddenmethod.filter.enabled=true

- Testing Branch Protection