# Note: The below are for reference, in case we want to create sql schema from the entities in jpa
# Once added to application.properties in main, and with h2 DB runtime dependency - This will generate a sql named create.sql once we do mvn spring-boot:run

spring.jpa.properties.javax.persistence.schema-generation.scripts.action=create
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=create.sql
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-source=metadata