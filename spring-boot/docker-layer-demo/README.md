# Spring Boot docker layer demo

Spring Boot Docker Layers allows you to separate your dependencies and application class files into different layers.

This allows your dependency layers to be re-used when possible, significantly reducing the size of new releases.

To enable the packaging of layers in the Maven build process, add the following configuration to your Maven POM.

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <layers>
                    <enabled>true</enabled>
                    <includeLayerTools>true</includeLayerTools>
                </layers>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Spring Boot will continue to produce a single fat JAR, but the packaging of the JAR is now ‘layered’.

We will use the Spring Boot layer tools to extract the layer files into our Docker image.

### Spring Boot Layer Tools

The above Maven configuration tells Spring Boot to add layer tools into the fat JAR.

To generate the fat JAR, use the command:
`mvn package`

You will find the fat JAR in the root of the `/targetdirectory`.

To list the layers packaged inside the JAR archive, use this command:
`java -Djarmode=layertools -jar target/docker-layer-demo-0.0.1-SNAPSHOT.jar list`

Layers will be extracted to the following folders:

```
dependencies
spring-boot-loader
snapshot-dependencies
application
```

All the fat JAR dependencies are in /dependencies. And your application class files are in /application.

If you would like to customize how the layers are extracted, please refer to
the [Spring Boot Maven plugin documentation here](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/#packaging.layers).

### Multi-Stage Docker Build

We will use a multi-stage Docker build to first extract the files and then build our desired Docker image.

#### Stage:01 - Builder

Here are the stage one Dockerfile commands:

```dockerfile
FROM openjdk:17-alpine as builder
WORKDIR application
ADD target/docker-layer-demo-0.0.1-SNAPSHOT.jar ./
RUN java -Djarmode=layertools -jar docker-layer-demo-0.0.1-SNAPSHOT.jar extract
```

These Docker file commands do the following:

- Start will the OpenJDK Java 17 alpine
- Create working directory called /application
- Copies the Spring Boot fat JAR into the working directory
- Calls the Spring Boot layer tools to extract the layer files

#### Stage:01 - Spring Boot Application Image

Dockerfile commands:

```dockerfile
FROM openjdk:17-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]
```

These Dockerfile commands do the following:

- Starts with the OpenJDK Java 17 alpine
- Creates working directory called /application
- Copies each layer directory into image
- Sets the entry point for the image

> Note: Remember, in the above, each COPY command will create an image layer. Thus, if your dependencies are not
> changing, a new layer is not created.

Complete Dockerfile

Here is the complete Dockerfile

```dockerfile
FROM openjdk:17-alpine as builder
WORKDIR application
ADD target/docker-layer-demo-0.0.1-SNAPSHOT.jar ./
RUN java -Djarmode=layertools -jar docker-layer-demo-0.0.1-SNAPSHOT.jar extract

FROM openjdk:17-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]
```

Run command to build docker:
`docker build -t abdulrehman1793/docker-layer-demo:1.0 .`

See the history docker layers:
`docker history abdulrehman1793/docker-layer-demo:1.0`

To build docker using maven execute command: `mvn docker:build`

#### Building docker image with maven



### Reference

[Why You Should be Using Spring Boot Docker Layers](https://springframework.guru/why-you-should-be-using-spring-boot-docker-layers/)