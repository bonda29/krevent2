# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project's JAR file into the container at /app
COPY target/your-spring-boot-app.jar /app/your-spring-boot-app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define environment variables (optional, you can override these at runtime)
ENV SPRING_DATASOURCE_URL=yourdatabase
ENV SPRING_DATASOURCE_USERNAME=yourusername
ENV SPRING_DATASOURCE_PASSWORD=yourpassword
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_JPA_DATABASE=MYSQL
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQLDialect
ENV SPRING_MAIL_FROM=your-email@example.com
ENV SPRING_MAIL_HOST=smtp.example.com
ENV SPRING_MAIL_PORT=26
ENV SPRING_MAIL_USERNAME=your-email-username
ENV SPRING_MAIL_PASSWORD=your-email-password
ENV APPLICATION_SECURITY_JWT_SECRET_KEY=your-secret-key
ENV APPLICATION_QR_CODE_PATH=/path/to/qr-code
ENV APPLICATION_URL_BASE=http://your-app-base-url
ENV STRIPE_SECRET_KEY=your-stripe-secret-key
ENV STRIPE_PRICE10=price_1
ENV STRIPE_PRICE15=price_2

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/your-spring-boot-app.jar"]
