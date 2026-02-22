# Step 1: Build stage using Maven and Java 17
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Runtime stage using Tomcat 9 and Java 17
# (Tomcat 9 works great with Java 17 and Spring 5.3.x)
FROM tomcat:9.0-jdk17-openjdk-slim

# Clean up default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the build artifact (WAR file) to Tomcat
COPY --from=build /target/CrudMVC-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
