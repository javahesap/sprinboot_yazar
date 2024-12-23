# OpenJDK 17 kullanarak bir base image alın
FROM openjdk:17-jdk-slim

# Uygulamanın jar dosyasını çalışma dizinine kopyalayın
COPY target/yazar.jar /app/app.jar

# Konteynerin çalışmaya başlayacağı komutu belirtin
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Spring Boot uygulamanızın dışarıya açacağı port (Varsayılan port 8080)
EXPOSE 8080
