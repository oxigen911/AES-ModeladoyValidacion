FROM gersua/openjdk8-ubuntu:latest
MAINTAINER jsvillanueva - jsvillanueva@javeriana.edu.co
ENV JAR_NAME IntermediateRouting.jar
ENV WORKSPACE /opt
WORKDIR ${WORKSPACE}
COPY target/${JAR_NAME} .
ENTRYPOINT java -jar ${JAR_NAME} -Djava.net.preferIPv4Stack=true 
EXPOSE 8082
