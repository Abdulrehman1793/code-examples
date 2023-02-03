This is simple spring boot docker project. Where we build docker image manually and deploy it to the docker hub. 

### Build docker image

`docker build -t abdulrehman1793/spring-docker-hello:1.0 .`

`docker images`

Get the Image id of `abdulrehman1793/spring-docker-hello` with Tag :`1.0`

And run image by replacing `d5bd9ade7394` to `YOUR_IMAGE_ID` 

`docker run -p 8081:8080 d5bd9ade7394`

### Deploy to docker hub

`docker push abdulrehman1793/spring-docker-hello:1.0`

