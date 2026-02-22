# Step 1: Build stage using Maven and Java 8
FROM maven:3.8.6-openjdk-8 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Runtime stage using Tomcat 9 and Java 8
FROM tomcat:9.0-jre8-slim

# Clean up default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the build artifact (WAR file) to Tomcat
# Ensure 'CrudMVC-1.0-SNAPSHOT.war' matches your actual WAR filename in /target
COPY --from=build /target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
