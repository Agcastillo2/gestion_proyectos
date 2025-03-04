# Imagen base de Java
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado por Maven al contenedor
COPY target/proyecto-0.0.1-SNAPSHOT.jar app.jar

# Puerto en el que se ejecutará la aplicación (debe coincidir con `server.port`)
EXPOSE 8003

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
