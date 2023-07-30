FROM maven:3.9.3-amazoncorretto-20

RUN mkdir /temp /hotel-management
ADD pom.xml /
RUN mvn verify clean --fail-never

COPY ./ /temp

WORKDIR /temp
RUN mvn clean install && cp target/*.jar /hotel-management/hotelmanagement.jar && rm -rf /temp && rm -rf ~/.m2
WORKDIR /hotel-management

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/hotel-management/hotelmanagement.jar"]