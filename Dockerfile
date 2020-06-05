FROM openjdk:12
EXPOSE 8080
ADD target/platforma.war platforma.war
ENTRYPOINT ["java", "-jar", "platforma.war"]