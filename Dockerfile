# Usar JDK 21 base
FROM eclipse-temurin:21-jdk-alpine

# Establecer directorio de trabajo
WORKDIR /app

# Copiar pom.xml y el wrapper de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copiar el código fuente
COPY src ./src

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Construir el proyecto sin tests
RUN ./mvnw clean package -DskipTests

# Puerto que expondrá la app
EXPOSE 10000

# Comando para arrancar la app
CMD ["java", "-jar", "target/fortalezaapi-0.0.1-SNAPSHOT.jar"]